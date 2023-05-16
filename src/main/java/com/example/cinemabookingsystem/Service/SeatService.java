package com.example.cinemabookingsystem.Service;

import com.example.cinemabookingsystem.Model.Seat;
import com.example.cinemabookingsystem.Repository.SeatRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SeatService {
    private final SeatRepo seatRepo;

    public List<Seat> getAllSeats(){
        return seatRepo.findAll();
    }

}
