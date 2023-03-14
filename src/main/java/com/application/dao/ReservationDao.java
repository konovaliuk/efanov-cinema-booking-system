package com.application.dao;

import com.application.model.Reservation;
import com.application.model.Screening;
import com.application.model.User;

public interface ReservationDao extends Dao<Reservation> {
    User getUserByReservation(long userId);
    Screening getScreeningByReservation(long screeningId);
    void getPaid(long reservationId);
    void getReserved(long reservationId);
    void activate(long reservationId);
}
