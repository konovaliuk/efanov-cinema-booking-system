package com.example.cinemabookingsystem.Service;

import com.example.cinemabookingsystem.Model.Movie;
import com.example.cinemabookingsystem.Model.Screening;
import com.example.cinemabookingsystem.Model.SeatReserved;
import com.example.cinemabookingsystem.Repository.ScreeningRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ScreeningService {
    private final ScreeningRepo screeningRepo;

    public List<Screening> getAllScreenings(){
        return screeningRepo.findAll();
    }

    public Screening getScreeningById(Long screeningId){
        return screeningRepo.findById(screeningId).orElseThrow(IllegalArgumentException::new);
    }

    public Screening pushScreening(Screening newScreening){
        return screeningRepo.save(newScreening);
    }

    public void updateScreening(Long id, Movie movie, Timestamp screeningStart){
        Optional<Screening> maybeScreeningToUpdate = screeningRepo.findById(id);
        if(maybeScreeningToUpdate.isEmpty())
            throw new IllegalArgumentException("Invalid screening");

        Screening screeningToUpdate = maybeScreeningToUpdate.get();
        screeningToUpdate.setMovie(movie);
        screeningToUpdate.setScreeningStart(screeningStart);

        screeningRepo.save(screeningToUpdate);
    }

    public void deleteScreening(Long id){
        screeningRepo.deleteById(id);
    }

    public List<SeatReserved> getSeatsByScreeningId(Long screening_id) {
        Screening screening = getScreeningById(screening_id);
        return new ArrayList<>(screening.getSeatReserved());
    }
}
