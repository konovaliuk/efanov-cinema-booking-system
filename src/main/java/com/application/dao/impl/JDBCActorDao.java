package com.application.dao.impl;

import com.application.dao.ActorDao;
import com.application.dao.DataSource;
import com.application.model.Actor;
import com.application.model.Movie;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCActorDao implements ActorDao {
    private final String GET_ACTOR_FILMS = "select movie_id, title, director, description, duration_min from movie join movie_actor using (movie_id) join actor_list using (actor_id) where actor_id = ?";
    private final String GET_ACTOR_BY_ID = "select * from actor_list where actor_id = ?";
    private final String GET_ACTORS = "select * from actor_list";
    private final String SAVE_ACTOR = "insert into actor_list(first_name, last_name, role) VALUES(?, ?, ?)";
    private final String UPDATE_ACTOR = "update actor_list set first_name = ?, last_name = ?, role = ? where actor_id = ?";
    private final String DELETE_ACTOR = "delete from actor_list where actor_id = ?";
    private static final Logger LOGGER = LogManager.getLogger(JDBCActorDao.class);
    private Connection connection;
    public void setConnection(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<Movie> getActorFilms(long actorId) {
        List<Movie> films = new ArrayList<>();
        try(PreparedStatement getActorFilms = connection.prepareStatement(GET_ACTOR_FILMS)){
            getActorFilms.setLong(1, actorId);
            ResultSet actorFilms = getActorFilms.executeQuery();
            while(actorFilms.next()){
                long movieId = actorFilms.getLong(1);
                String title = actorFilms.getString(2);
                String director = actorFilms.getString(3);
                String description = actorFilms.getString(4);
                int durationMin = actorFilms.getInt(5);
                films.add(new Movie(movieId, title, director, description, durationMin));
            }
            return films;
        } catch (SQLException e) {
            LOGGER.error("Error while getting actors", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Actor get(long id) {
        try(PreparedStatement getMovie = connection.prepareStatement(GET_ACTOR_BY_ID)){
            getMovie.setLong(1, id);
            ResultSet movie = getMovie.executeQuery();
            if (movie.next()) {
                String firstName = movie.getString(2);
                String lastName = movie.getString(3);
                String role = movie.getString(4);
                return new Actor(id, firstName, lastName, role);
            }
            return null;
        } catch (SQLException e) {
            LOGGER.error("Error while getting actor by id", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Actor> getAll() {
        List<Actor> actors = new ArrayList<>();
        try(Statement getActors = connection.createStatement()){
            ResultSet actorsSet = getActors.executeQuery(GET_ACTORS);
            while(actorsSet.next()){
                long actorId = actorsSet.getLong(1);
                String firstName = actorsSet.getString(2);
                String lastName = actorsSet.getString(3);
                String role = actorsSet.getString(4);
                actors.add(new Actor(actorId, firstName, lastName, role));
            }
            return actors;
        } catch (SQLException e) {
            LOGGER.error("Can't get movies", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Actor actor) {
        try(PreparedStatement saveMovie = connection.prepareStatement(SAVE_ACTOR)){
            saveMovie.setString(1, actor.getFirst_name());
            saveMovie.setString(2, actor.getLast_name());
            saveMovie.setString(3, actor.getRole());
            saveMovie.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot save movie", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Actor actor) {
        try(PreparedStatement updateActor = connection.prepareStatement(UPDATE_ACTOR)){
            updateActor.setString(1, actor.getFirst_name());
            updateActor.setString(2, actor.getLast_name());
            updateActor.setString(3, actor.getRole());
            updateActor.setLong(4, actor.getID());
            updateActor.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot update actor", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement deleteActor = connection.prepareStatement(DELETE_ACTOR)){
            deleteActor.setLong(1, id);
            deleteActor.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot delete actor", e);
            throw new RuntimeException(e);
        }
    }
}
