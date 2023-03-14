package com.application.dao.impl;

import com.application.dao.SeatDao;
import com.application.model.Seat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSeatDao implements SeatDao {
    private final String GET_SEAT_BY_ID = "select * from seat where seat_id = ?";
    private final String GET_SEATS = "select * from seat";
    private final String SAVE_SEAT = "insert into seat(row, number) values(?, ?)";
    private final String UPDATE_SEAT = "update seat set row = ?, number = ? where seat_id = ?";
    private final String DELETE_SEAT = "delete from seat where seat_id = ?";
    private static final Logger LOGGER = LogManager.getLogger(JDBCSeatDao.class);
    private Connection connection;
    public void setConnection(Connection connection){
        this.connection = connection;
    }
    @Override
    public Seat get(long id) {
        try(PreparedStatement getSeatById = connection.prepareStatement(GET_SEAT_BY_ID)){
            getSeatById.setLong(1, id);
            ResultSet seat = getSeatById.executeQuery();
            if(seat.next()){
                int row = seat.getInt(2);
                int number = seat.getInt(3);
                return new Seat(id, row, number);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Seat> getAll() {
        List<Seat> seats = new ArrayList<>();
        try(Statement getSeats = connection.createStatement()){
            ResultSet seat = getSeats.executeQuery(GET_SEATS);
            while(seat.next()){
                long seatId = seat.getLong(1);
                int row = seat.getInt(2);
                int number = seat.getInt(3);
                seats.add(new Seat(seatId, row, number));
            }
            return seats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Seat seat) {
        try(PreparedStatement saveSeat = connection.prepareStatement(SAVE_SEAT)){
            saveSeat.setLong(1, seat.getRow());
            saveSeat.setLong(2, seat.getNumber());
            saveSeat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Seat seat) {
        try(PreparedStatement updateSeat = connection.prepareStatement(UPDATE_SEAT)){
            updateSeat.setLong(1, seat.getRow());
            updateSeat.setLong(2, seat.getNumber());
            updateSeat.setLong(3, seat.getID());
            updateSeat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(PreparedStatement updateSeat = connection.prepareStatement(DELETE_SEAT)){
            updateSeat.setLong(1, id);
            updateSeat.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
