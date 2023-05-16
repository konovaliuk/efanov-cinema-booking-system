package com.example.cinemabookingsystem.Repository;

import com.example.cinemabookingsystem.Model.Reservation;
import com.example.cinemabookingsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
}
