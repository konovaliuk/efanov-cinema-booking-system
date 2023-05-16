package com.example.cinemabookingsystem.Service;

import com.example.cinemabookingsystem.Model.Screening;
import com.example.cinemabookingsystem.Repository.MovieRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cinemabookingsystem.Model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovieService {
    private final MovieRepo movieRepo;

    public List<Movie> getAllMovies(){
        return movieRepo.findAll();
    }

    public Movie getMovieById(Long id) throws IllegalArgumentException{
        Optional<Movie> maybeMovie = movieRepo.findById(id);
        if(maybeMovie.isEmpty())
            throw new IllegalArgumentException("No such movie");
        return maybeMovie.get();
    }

    public Movie pushMovie(Movie movie){
        return movieRepo.save(movie);
    }

    public void updateMovie(Long id, Movie movie) throws IllegalArgumentException {
        Optional<Movie> maybeMovieToUpdate = movieRepo.findById(id);
        if(maybeMovieToUpdate.isEmpty())
            throw new IllegalArgumentException("Invalid movie");

        Movie movieToUpdate = maybeMovieToUpdate.get();

        if(movie.getTitle() != null && !movie.getTitle().isBlank())
            movieToUpdate.setTitle(movie.getTitle());
        if(movie.getDirector() != null && !movie.getDirector().isBlank())
            movieToUpdate.setDirector(movie.getDirector());
        if(movie.getDescription() != null && !movie.getDescription().isBlank())
            movieToUpdate.setDescription(movie.getDescription());
        if(movie.getDurationMin() != null)
            movieToUpdate.setDurationMin(movie.getDurationMin());
        if(movie.getActors() != null && !movie.getActors().isEmpty())
            movieToUpdate.setActors(movie.getActors());

        movieRepo.save(movieToUpdate);
    }

    public List<Screening> getAllScreeningByMovieId(Long movie_id) {
        Movie movie = getMovieById(movie_id);
        return new ArrayList<>(movie.getScreenings());
    }

    public void deleteMovie(Long id) {
        movieRepo.deleteById(id);
    }
}
