package com.example.cinemabookingsystem.Service;

import com.example.cinemabookingsystem.Model.Reservation;
import com.example.cinemabookingsystem.Model.Seat;
import com.example.cinemabookingsystem.Repository.ReservationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class ReservationService {
    ReservationRepo reservationRepo;

    public Reservation reserveSeats(Reservation reservation){
        reservation.setReserved(true);
        return reservationRepo.save(reservation);
    }
}
