package com.application.dao.impl;

import com.application.dao.SeatReservedDao;
import com.application.model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSeatReservedDao implements SeatReservedDao {
    private final String GET_SEAT_RESERVED_BY_ID = "select * from seat_reserved where seat_reserved_id = ?";
    private final String GET_SEAT_RESERVED = "select * from seat_reserved";
    private final String SAVE_SEAT_RESERVED = "insert into seat_reserved(seat_id, reservation_id, screening_id) VALUES(?, ?, ?)";
    private final String UPDATE_SEAT_RESERVED = "update seat_reserved set seat_id = ?, reservation_id = ?, screening_id = ? where seat_reserved_id = ?";
    private final String DELETE_SEAT_RESERVED = "delete from seat_reserved where seat_reserved_id = ?";
    private final String GET_RESERVED_BY_SCREENING = "select seat_id, row, number from seat s " +
                                                    "join seat_reserved sr using (seat_id) " +
                                                    "join reservation r on r.reservation_id = sr.reservation_id " +
                                                    "join screening scr on scr.screening_id = sr.screening_id " +
                                                    "where scr.screening_id = ? and r.reserved = true";
    private final String GET_RESERVED_BY_SCREENING_AND_USER = "select seat_id, row, number from seat s " +
            "join seat_reserved sr using (seat_id) " +
            "join reservation r on r.reservation_id = sr.reservation_id " +
            "join screening sc on sc.screening_id = sr.screening_id " +
            "join user_list ul on r.user_id = ul.user_id " +
            "where sc.screening_id = ? and r.reserved = true and ul.user_id = ?" ;
    private static final Logger LOGGER = LogManager.getLogger(JDBCSeatReservedDao.class);
    private Connection connection;
    public void setConnection(Connection connection){
        this.connection = connection;
    }
    @Override
    public SeatReserved get(long id) {
        try(PreparedStatement getSeatById = connection.prepareStatement(GET_SEAT_RESERVED_BY_ID)){
            getSeatById.setLong(1, id);
            ResultSet seatReserved = getSeatById.executeQuery();
            if(seatReserved.next()){
                long seatId = seatReserved.getLong(2);
                Seat seat = getSeat(seatId);
                long reservationId = seatReserved.getLong(3);
                Reservation reservation = getReservation(reservationId);
                long screeningId = seatReserved.getLong(4);
                Screening screening = getScreening(screeningId);
                return new SeatReserved(id, seat, reservation, screening);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SeatReserved> getAll() {
        List<SeatReserved> seats = new ArrayList<>();
        try(Statement getSeatById = connection.createStatement()){
            ResultSet seatSet = getSeatById.executeQuery(GET_SEAT_RESERVED);
            while(seatSet.next()){
                long seatReservedId = seatSet.getLong(1);
                long seatId = seatSet.getLong(2);
                Seat seat = getSeat(seatId);
                long reservationId = seatSet.getLong(3);
                Reservation reservation = getReservation(reservationId);
                long screeningId = seatSet.getLong(4);
                Screening screening = getScreening(screeningId);
                seats.add(new SeatReserved(seatReservedId, seat, reservation, screening));
            }
            return seats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(SeatReserved seatReserved) {
        try(PreparedStatement saveSeat = connection.prepareStatement(SAVE_SEAT_RESERVED)){
            saveSeat.setLong(1, seatReserved.getSeat().getID());
            saveSeat.setLong(2, seatReserved.getReservation().getID());
            saveSeat.setLong(3, seatReserved.getScreening().getID());
            saveSeat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(SeatReserved seatReserved) {
        try(PreparedStatement updateSeat = connection.prepareStatement(UPDATE_SEAT_RESERVED)){
            updateSeat.setLong(1, seatReserved.getSeat().getID());
            updateSeat.setLong(2, seatReserved.getReservation().getID());
            updateSeat.setLong(3, seatReserved.getScreening().getID());
            updateSeat.setLong(4, seatReserved.getID());
            updateSeat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement deleteSeat = connection.prepareStatement(DELETE_SEAT_RESERVED)){
            deleteSeat.setLong(1, id);
            deleteSeat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Seat> getReservedSeats(Screening screening) {
        List<Seat> seats = new ArrayList<>();
        try(PreparedStatement reservedSeats = connection.prepareStatement(GET_RESERVED_BY_SCREENING)){
            reservedSeats.setLong(1, screening.getID());
            ResultSet seatsSet = reservedSeats.executeQuery();
            while(seatsSet.next()){
                long seatId = seatsSet.getLong(1);
                int row = seatsSet.getInt(2);
                int number = seatsSet.getInt(3);
                seats.add(new Seat(seatId, row, number));
            }
            return seats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Seat> getReservedSeatsByUser(Screening screening, User user) {
        List<Seat> seats = new ArrayList<>();
        try(PreparedStatement reservedSeats = connection.prepareStatement(GET_RESERVED_BY_SCREENING_AND_USER)){
            reservedSeats.setLong(1, screening.getID());
            reservedSeats.setLong(2, user.getID());
            ResultSet seatsSet = reservedSeats.executeQuery();
            while(seatsSet.next()){
                long seatId = seatsSet.getLong(1);
                int row = seatsSet.getInt(2);
                int number = seatsSet.getInt(3);
                seats.add(new Seat(seatId, row, number));
            }
            return seats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Seat getSeat(long seatId) {
        JDBCSeatDao seatDao = new JDBCSeatDao();
        seatDao.setConnection(connection);
        Seat seat = seatDao.get(seatId);
        return seat;
    }

    @Override
    public Reservation getReservation(long reservationId) {
        JDBCReservationDao reservationDao = new JDBCReservationDao();
        reservationDao.setConnection(connection);
        Reservation reservation = reservationDao.get(reservationId);
        return reservation;
    }

    @Override
    public Screening getScreening(long screeningId) {
        JDBCScreeningDao screeningDao = new JDBCScreeningDao();
        screeningDao.setConnection(connection);
        Screening screening = screeningDao.get(screeningId);
        return screening;
    }
}
