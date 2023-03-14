package com.application.dao.impl;

import com.application.dao.DataSource;
import com.application.dao.MovieDao;
import com.application.dao.util.PasswordEncoder;
import com.application.model.Actor;
import com.application.model.Movie;
import com.application.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCMovieDao implements MovieDao {
    private final String GET_FILM_ACTORS = "select actor_id, first_name, last_name, role from actor_list join movie_actor using (actor_id) join movie using (movie_id) where movie_id = ?";
    private final String GET_FILM_BY_ID = "select * from movie where movie_id = ?";
    private final String GET_FILMS = "select * from movie";
    private final String SAVE_FILM = "insert into movie(title, director, description, duration_min) VALUES(?, ?, ?, ?)";
    private final String UPDATE_MOVIE = "update movie set title = ?, director = ?, description = ?, duration_min = ? where movie_id = ?";
    private final String DELETE_MOVIE = "delete from movie where movie_id = ?";
    private final String GET_FILMS_BY_DIRECTOR = "select * from movie where director = ?";
    private static final Logger LOGGER = LogManager.getLogger(JDBCMovieDao.class);
    private Connection connection;
    public void setConnection(Connection connection){
        this.connection = connection;
    }

    @Override
    public Movie get(long id) {
        try(PreparedStatement getMovie = connection.prepareStatement(GET_FILM_BY_ID)){
            getMovie.setLong(1, id);
            ResultSet movie = getMovie.executeQuery();
            if (movie.next()) {
                String title = movie.getString(2);
                String director = movie.getString(3);
                String description = movie.getString(4);
                int duration_min = movie.getInt(5);
                return new Movie(id, title, director, description, duration_min);
            }
            return null;
        } catch (SQLException e) {
            LOGGER.error("Error while getting movie by id", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Movie> getAll() {
        try(Statement getMovies = connection.createStatement()){
            ResultSet movies = getMovies.executeQuery(GET_FILMS);
            List<Movie> movieList = movieSetToList(movies);
            return movieList;

        } catch (SQLException e) {
            LOGGER.error("Can't get movies", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Movie movie) {
        try(PreparedStatement saveMovie = connection.prepareStatement(SAVE_FILM)){
            saveMovie.setString(1, movie.getTitle());
            saveMovie.setString(2, movie.getDirector());
            saveMovie.setString(3, movie.getDescription());
            saveMovie.setInt(4, movie.getDurationTime());
            saveMovie.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot save movie", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Movie movie) {
        try(PreparedStatement updateMovie = connection.prepareStatement(UPDATE_MOVIE)){
            updateMovie.setString(1, movie.getTitle());
            updateMovie.setString(2, movie.getDirector());
            updateMovie.setString(3, movie.getDescription());
            updateMovie.setInt(4, movie.getDurationTime());
            updateMovie.setLong(5, movie.getID());
            updateMovie.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot update movie", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement deleteMovie = connection.prepareStatement(DELETE_MOVIE)){
            deleteMovie.setLong(1, id);
            deleteMovie.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot delete movie", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Movie> getFilmsByDirector(String director) {
        try(PreparedStatement getFilmsByDirector = connection.prepareStatement(GET_FILMS_BY_DIRECTOR)){
            getFilmsByDirector.setString(1, director);
            ResultSet movies = getFilmsByDirector.executeQuery();
            List<Movie> moviesByDirector = movieSetToList(movies);
            return moviesByDirector;
        } catch (SQLException e) {
            LOGGER.error("Cannot find such movies", e);
            throw new RuntimeException(e);
        }
    }

    private List<Movie> movieSetToList(ResultSet moviesSet) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        while(moviesSet.next()){
            long movieId = moviesSet.getLong(1);
            String title = moviesSet.getString(2);
            String director = moviesSet.getString(3);
            String description = moviesSet.getString(4);
            int duration_min = moviesSet.getInt(5);
            movies.add(new Movie(movieId, title, director, description, duration_min));
        }
        return movies;
    }

    @Override
    public List<Actor> getFilmActors(long filmId) {
        List<Actor> actors = new ArrayList<>();
        try(PreparedStatement getFilmActors = connection.prepareStatement(GET_FILM_ACTORS)){
            getFilmActors.setLong(1, filmId);
            ResultSet filmActors = getFilmActors.executeQuery();
            while(filmActors.next()){
                long actorId = filmActors.getLong(1);
                String firstName = filmActors.getString(2);
                String lastName = filmActors.getString(3);
                String role = filmActors.getString(4);
                actors.add(new Actor(actorId, firstName, lastName, role));
            }
            return actors;
        } catch (SQLException e) {
            LOGGER.error("Error while getting actors", e);
            throw new RuntimeException(e);
        }
    }
}
