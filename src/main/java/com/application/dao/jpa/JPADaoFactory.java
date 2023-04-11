package com.application.dao.jpa;

import com.application.dao.*;

public class JPADaoFactory extends DaoFactory {
    @Override
    public ActorDao createActorDao() {
        return new JPAActorDao();
    }
    @Override
    public MovieDao createMovieDao() {
        return new JPAMovieDao();
    }

    @Override
    public ReservationDao createReservationDao() {
        return new JPAReservationDao();
    }

    @Override
    public ScreeningDao createScreeningDao() {
        return new JPAScreeningDao();
    }

    @Override
    public SeatDao createSeatDao() {
        return new JPASeatDao();
    }
    @Override
    public UserDao createUserDao() {
        return new JPAUserDao();
    }
    @Override
    public SeatReservedDao createSeatReservedDao() {
        return null;
    }
    @Override
    public MovieActorDao createMovieActorDao() {
        return null;
    }
}
