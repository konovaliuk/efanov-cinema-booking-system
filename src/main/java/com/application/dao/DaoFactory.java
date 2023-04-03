package com.application.dao;

import com.application.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {

    public abstract ActorDao createActorDao();
    public abstract MovieActorDao createMovieActorDao();
    public abstract MovieDao createMovieDao();
    public abstract ReservationDao createReservationDao();
    public abstract ScreeningDao createScreeningDao();
    public abstract SeatDao createSeatDao();
    public abstract SeatReservedDao createSeatReservedDao();
    public abstract UserDao createUserDao();

}
