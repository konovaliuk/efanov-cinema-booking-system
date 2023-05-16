package com.example.cinemabookingsystem.Repository;

import com.example.cinemabookingsystem.Model.Screening;
import com.example.cinemabookingsystem.Model.SeatReserved;
import com.example.cinemabookingsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatReservedRepo extends JpaRepository<SeatReserved, Long> {

    List<SeatReserved> getSeatReservedByScreening(Screening screening);
    List<SeatReserved> getSeatReservedByReservation_User(User user);
}
