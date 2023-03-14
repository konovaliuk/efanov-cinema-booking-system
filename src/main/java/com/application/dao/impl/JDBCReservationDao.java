package com.application.dao.impl;

import com.application.dao.DataSource;
import com.application.dao.ReservationDao;
import com.application.model.Reservation;
import com.application.model.Screening;
import com.application.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCReservationDao implements ReservationDao {
    private final String GET_RESERVATION_BY_ID = "select * from reservation where reservation_id = ?";
    private final String GET_RESERVATIONS = "select * from reservation";
    private final String GET_PAID = "update reservation set paid = true where reservation_id = ?";
    private final String GET_RESERVED = "update reservation set reserved = true where reservation_id = ?";
    private final String GET_ACTIVE = "update reservation set active = true where reservation_id = ?";
    private final String SAVE_RESERVATION = "insert into reservation(user_id, screening_id, reserved, paid, active) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_RESERVATION = "update reservation set user_id = ?, screening_id = ?, reserved = ?, paid = ?, active = ? where reservation_id = ?";
    private final String DELETE_RESERVATION = "delete from reservation where reservation_id = ?";
    private static final Logger LOGGER = LogManager.getLogger(JDBCReservationDao.class);
    private Connection connection;
    public void setConnection(Connection connection){
        this.connection = connection;
    }
    @Override
    public Reservation get(long id) {
        try(PreparedStatement getReservation = connection.prepareStatement(GET_RESERVATION_BY_ID)){
            getReservation.setLong(1, id);
            ResultSet reservation = getReservation.executeQuery();
            if(reservation.next()){
                long userId = reservation.getLong(2);
                long screeningId = reservation.getLong(3);
                boolean reserved = reservation.getBoolean(4);
                boolean paid = reservation.getBoolean(5);
                boolean active = reservation.getBoolean(6);
                User user = getUserByReservation(userId);
                Screening screening = getScreeningByReservation(screeningId);
                return new Reservation(id, user, screening, reserved, paid, active);
            }
            return null;

        } catch (SQLException e) {
            LOGGER.error("Cannot get such reservation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        try(Statement getReservation = connection.createStatement()){
            ResultSet reservation = getReservation.executeQuery(GET_RESERVATIONS);
            while(reservation.next()){
                long id = reservation.getLong(1);
                long userId = reservation.getLong(2);
                long screeningId = reservation.getLong(3);
                boolean reserved = reservation.getBoolean(4);
                boolean paid = reservation.getBoolean(5);
                boolean active = reservation.getBoolean(6);
                User user = getUserByReservation(userId);
                Screening screening = getScreeningByReservation(screeningId);
                reservations.add(new Reservation(id, user, screening, reserved, paid, active));
            }
            return reservations;
        } catch (SQLException e) {
            LOGGER.error("Cannot get all reservations", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Reservation reservation) {
        try(PreparedStatement saveReservation = connection.prepareStatement(SAVE_RESERVATION)){
            saveReservation.setLong(1, reservation.getUser().getID());
            saveReservation.setLong(2, reservation.getScreening().getID());
            saveReservation.setBoolean(3, reservation.isReserved());
            saveReservation.setBoolean(4, reservation.isPaid());
            saveReservation.setBoolean(5, reservation.isActive());
            saveReservation.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot save reservation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Reservation reservation) {
        try(PreparedStatement updateReservation = connection.prepareStatement(UPDATE_RESERVATION)){
            updateReservation.setLong(1, reservation.getUser().getID());
            updateReservation.setLong(2, reservation.getScreening().getID());
            updateReservation.setBoolean(3, reservation.isReserved());
            updateReservation.setBoolean(4, reservation.isPaid());
            updateReservation.setBoolean(5, reservation.isActive());
            updateReservation.setLong(6, reservation.getID());
            updateReservation.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot update reservation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement deleteReservation = connection.prepareStatement(DELETE_RESERVATION)){
            deleteReservation.setLong(1, id);
            deleteReservation.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot delete reservation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByReservation(long userId) {
        JDBCUserDao userDao = new JDBCUserDao();
        userDao.setConnection(connection);
        User user = userDao.get(userId);
        return user;
    }

    @Override
    public Screening getScreeningByReservation(long screeningId) {
        JDBCScreeningDao userDao = new JDBCScreeningDao();
        userDao.setConnection(connection);
        Screening user = userDao.get(screeningId);
        return user;
    }

    @Override
    public void getPaid(long reservationId) {
        try(PreparedStatement getPaid = connection.prepareStatement(GET_PAID)){
            getPaid.setLong(1, reservationId);
            getPaid.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot be paid such reservation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getReserved(long reservationId) {
        try(PreparedStatement getReserved = connection.prepareStatement(GET_RESERVED)){
            getReserved.setLong(1, reservationId);
            getReserved.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot be reserved such reservation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void activate(long reservationId) {
        try(PreparedStatement getActivated = connection.prepareStatement(GET_ACTIVE)){
            getActivated.setLong(1, reservationId);
            getActivated.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot be activated such reservation", e);
            throw new RuntimeException(e);
        }
    }
}
