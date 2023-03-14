package com.application.dao;

import com.application.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SeatReservedDao extends Dao<SeatReserved> {
    List<Seat> getReservedSeats(Screening screening);
    List<Seat> getReservedSeatsByUser(Screening screening, User user);

    Seat getSeat(long seatId);
    Reservation getReservation(long reservationId);
    Screening getScreening(long screeningId);

}
