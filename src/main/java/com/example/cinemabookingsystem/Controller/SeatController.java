package com.example.cinemabookingsystem.Controller;

import com.example.cinemabookingsystem.Model.Seat;
import com.example.cinemabookingsystem.Service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;
    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeats(){
        List<Seat> users = seatService.getAllSeats();
        return ResponseEntity.ok(users);
    }
}
