package com.application.dao.impl;

import com.application.dao.DataSource;
import com.application.dao.MovieActorDao;
import com.application.model.MovieActor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCMovieActorDao implements MovieActorDao {
    private final String SAVE_MOVIE_ACTOR = "insert into movie_actor(movie_id, actor_id) VALUES(?, ?)";
    private final String DELETE_MOVIE_ACTOR = "delete from movie_actor where movie_id = ? and actor_id = ?";

    private static final Logger LOGGER = LogManager.getLogger(JDBCMovieActorDao.class);
    private Connection connection;
    public void setConnection(Connection connection){
        this.connection = connection;
    }
    @Override
    public void save(MovieActor movieActor) {
        try(PreparedStatement saveMovieActor = connection.prepareStatement(SAVE_MOVIE_ACTOR)){
            saveMovieActor.setLong(1, movieActor.getMovieId());
            saveMovieActor.setLong(2, movieActor.getActorId());
            saveMovieActor.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error while inserting movie_actor dependency", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(MovieActor movieActor) {
        try(PreparedStatement deleteMovieActor = connection.prepareStatement(DELETE_MOVIE_ACTOR)){
            deleteMovieActor.setLong(1, movieActor.getMovieId());
            deleteMovieActor.setLong(2, movieActor.getActorId());
            deleteMovieActor.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error while deleting movie_actor dependency", e);
            throw new RuntimeException(e);
        }
    }
}
