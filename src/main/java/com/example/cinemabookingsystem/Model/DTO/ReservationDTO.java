package com.example.cinemabookingsystem.Model.DTO;

import com.example.cinemabookingsystem.Model.Screening;
import com.example.cinemabookingsystem.Model.SeatReserved;
import com.example.cinemabookingsystem.Model.User;

import java.util.Set;

public record ReservationDTO(User user, Screening screening, Boolean reserved, Boolean active, Set<SeatReserved> bookedSeats) {
}
