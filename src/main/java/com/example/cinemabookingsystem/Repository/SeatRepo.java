package com.example.cinemabookingsystem.Repository;

import com.example.cinemabookingsystem.Model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepo extends JpaRepository<Seat, Long> {

}
