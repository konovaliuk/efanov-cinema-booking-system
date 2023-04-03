package com.application.dao.impl;

import com.application.dao.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static com.application.dao.DataSource.getConnection;

public class JDBCDaoFactory extends DaoFactory {
    Connection connection;

    public JDBCDaoFactory(Connection connection){
        this.connection = connection;
    }

    @Override
    public ActorDao createActorDao() {
        return new JDBCActorDao(connection);
    }

    @Override
    public MovieActorDao createMovieActorDao() {
        return new JDBCMovieActorDao(connection);
    }

    @Override
    public MovieDao createMovieDao() {
        return new JDBCMovieDao(connection);
    }

    @Override
    public ReservationDao createReservationDao() {
        return new JDBCReservationDao(connection);
    }

    @Override
    public ScreeningDao createScreeningDao() {
        return new JDBCScreeningDao(connection);
    }

    @Override
    public SeatDao createSeatDao() {
        return new JDBCSeatDao(connection);
    }

    @Override
    public SeatReservedDao createSeatReservedDao() {
        return new JDBCSeatReservedDao(connection);
    }

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(connection);
    }
}
