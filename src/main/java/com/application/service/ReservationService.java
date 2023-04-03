package com.application.service;

import com.application.dao.DataSource;
import com.application.dao.ReservationDao;
import com.application.dao.SeatReservedDao;
import com.application.dao.impl.JDBCDaoFactory;
import com.application.model.Reservation;
import com.application.model.Seat;
import com.application.model.SeatReserved;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationService {
    public Reservation reserveSeat(Reservation reservation, ArrayList<Seat> seats){
        try(Connection connection = DataSource.getConnection()){
            try{
                connection.setAutoCommit(false);
                ReservationDao reservationDao = new JDBCDaoFactory(connection).createReservationDao();
                reservationDao.save(reservation);
                SeatReservedDao seatReservedDao = new JDBCDaoFactory(connection).createSeatReservedDao();
                for(Seat seat : seats){
                    seatReservedDao.save(new SeatReserved(1, seat, reservation, reservation.getScreening()));
                }
                reservationDao.getReserved(reservation.getID());
                reservationDao.getPaid(reservation.getID());
                connection.commit();
                return reservation;
            }
            catch(Exception e){
                connection.rollback();
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
