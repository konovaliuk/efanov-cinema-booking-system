package com.example.cinemabookingsystem.Controller;

import com.example.cinemabookingsystem.Model.Screening;
import com.example.cinemabookingsystem.Model.SeatReserved;
import com.example.cinemabookingsystem.Service.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/screenings")
public class ScreeningController {
    private final ScreeningService screeningService;
    @GetMapping
    public ResponseEntity<List<Screening>> getAllScreenings() {
        List<Screening> screenings = screeningService.getAllScreenings();
        return ResponseEntity.status(HttpStatus.OK).body(screenings);
    }

    @GetMapping("/{screeningId}")
    public ResponseEntity<Screening> getScreenById(@PathVariable Long screeningId) {
        try{
            Screening screening = screeningService.getScreeningById(screeningId);
            return ResponseEntity.ok(screening);
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = {"/{screeningId}"}, method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteScreening(@PathVariable Long screeningId){
        screeningService.deleteScreening(screeningId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{screeningId}/seatsReserved")
    public ResponseEntity<?> getSeatsByScreeningId(@PathVariable Long screeningId) {
        List<SeatReserved> seats = screeningService.getSeatsByScreeningId(screeningId);
        return ResponseEntity.status(HttpStatus.OK).body(seats);
    }
}
