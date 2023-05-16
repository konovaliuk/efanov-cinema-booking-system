package com.example.cinemabookingsystem.Controller;

import com.example.cinemabookingsystem.Model.DTO.ReservationDTO;
import com.example.cinemabookingsystem.Model.Reservation;
import com.example.cinemabookingsystem.Model.Screening;
import com.example.cinemabookingsystem.Model.SeatReserved;
import com.example.cinemabookingsystem.Model.User;
import com.example.cinemabookingsystem.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> reserveSeats(@RequestBody ReservationDTO reservation){
        User user = reservation.user();
        Screening screening = reservation.screening();
        Boolean reserved = reservation.reserved();
        Boolean active = reservation.active();
        Set<SeatReserved> reservedSeats = reservation.bookedSeats();

        Reservation reservationToAdd = reservationService.reserveSeats(new Reservation(user, screening, reserved, active, reservedSeats));
        String userURI = String.format("/users/%d", reservationToAdd.getId());

        return ResponseEntity.created(URI.create(userURI)).build();
    }
}
