package com.application.service;

import com.application.dao.DataSource;
import com.application.dao.SeatReservedDao;
import com.application.dao.impl.JDBCDaoFactory;
import com.application.model.Screening;
import com.application.model.Seat;
import com.application.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SeatReservedService {
    public List<Seat> getReservedSeatsByScreening(Screening screening){
        try(Connection connection = DataSource.getConnection()) {
            SeatReservedDao seatReservedDao = new JDBCDaoFactory(connection).createSeatReservedDao();
            List<Seat> seatsReserved = seatReservedDao.getReservedSeats(screening);
            return seatsReserved;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Seat> getReservedSeatsByUser(Screening screening, User user){
        try(Connection connection = DataSource.getConnection()) {
            SeatReservedDao seatReservedDao = new JDBCDaoFactory(connection).createSeatReservedDao();
            List<Seat> seatsReservedByUser = seatReservedDao.getReservedSeatsByUser(screening, user);
            return seatsReservedByUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
