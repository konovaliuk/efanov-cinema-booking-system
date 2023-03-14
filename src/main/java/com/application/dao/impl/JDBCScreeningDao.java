package com.application.dao.impl;

import com.application.dao.DataSource;
import com.application.dao.ScreeningDao;
import com.application.model.Movie;
import com.application.model.Screening;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCScreeningDao implements ScreeningDao {
    private final String GET_SCREENING_BY_ID = "select * from screening where screening_id = ?";
    private final String GET_SCREENING = "select * from screening";
    private final String SAVE_SCREENING = "insert into screening(movie_id, screening_start) VALUES (?, ?)";
    private final String UPDATE_SCREENING = "update screening set movie_id = ?, screening_start = ? where screening_id = ?";
    private final String DELETE_SCREENING = "delete from screening where movie_id = ?";
    private static final Logger LOGGER = LogManager.getLogger(JDBCScreeningDao.class);
    private Connection connection;
    public void setConnection(Connection connection){
        this.connection = connection;
    }
    @Override
    public Screening get(long id) {
        try(PreparedStatement getScreening = connection.prepareStatement(GET_SCREENING_BY_ID)){
            getScreening.setLong(1, id);
            ResultSet screening = getScreening.executeQuery();
            if (screening.next()) {
                long movieId = screening.getLong(2);
                Movie movie = getFilmScreening(movieId);
                Timestamp screeningStart = screening.getTimestamp(3);
                return new Screening(id, movie, screeningStart);
            }
            return null;

        } catch (SQLException e) {
            LOGGER.error("Error while getting screening by id", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Screening> getAll() {
        List<Screening> screenings = new ArrayList<>();
        try(Statement getScreening = connection.createStatement()){
            ResultSet screeningSet = getScreening.executeQuery(GET_SCREENING);
            if (screeningSet.next()) {
                long screeningId = screeningSet.getLong(1);
                long movieId = screeningSet.getLong(2);
                Movie movie = getFilmScreening(movieId);
                Timestamp screeningStart = screeningSet.getTimestamp(3);
                screenings.add(new Screening(screeningId, movie, screeningStart));
            }
            return screenings;

        } catch (SQLException e) {
            LOGGER.error("Error while getting screenings by id", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Screening screening) {
        try(PreparedStatement saveReservation = connection.prepareStatement(SAVE_SCREENING)){
            saveReservation.setLong(1, screening.getMovie().getID());
            saveReservation.setTimestamp(2, screening.getScreeningStart());
            saveReservation.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot save screening", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Screening screening) {
        try(PreparedStatement updateScreening = connection.prepareStatement(UPDATE_SCREENING)){
            updateScreening.setLong(1, screening.getMovie().getID());
            updateScreening.setTimestamp(2, screening.getScreeningStart());
            updateScreening.setLong(3, screening.getID());
            updateScreening.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot update screening", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement deleteScreening = connection.prepareStatement(DELETE_SCREENING)){
            deleteScreening.setLong(1, id);
            deleteScreening.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot delete screening", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Movie getFilmScreening(long filmId) {
        JDBCMovieDao movieDao = new JDBCMovieDao();
        movieDao.setConnection(connection);
        Movie movie = movieDao.get(filmId);
        return movie;
    }
}
