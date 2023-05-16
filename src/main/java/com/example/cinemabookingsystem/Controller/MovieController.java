package com.example.cinemabookingsystem.Controller;

import com.example.cinemabookingsystem.Model.Actor;
import com.example.cinemabookingsystem.Model.DTO.MovieDTO;
import com.example.cinemabookingsystem.Model.Movie;
import com.example.cinemabookingsystem.Model.Screening;
import com.example.cinemabookingsystem.Service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.status(HttpStatus.OK).body(movies);
    }

    @GetMapping("/{movie_id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long movie_id) {
        try {
            final Movie movie = movieService.getMovieById(movie_id);
            return ResponseEntity.ok(movie);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{movie_id}/screenings")
    public ResponseEntity<List<Screening>> getAllScreeningByMovieId(@PathVariable Long movie_id) {
        List<Screening> screenings = movieService.getAllScreeningByMovieId(movie_id);
        return ResponseEntity.status(HttpStatus.OK).body(screenings);
    }

    @RequestMapping(value = "/{movieId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteMovieById(@PathVariable Long movieId) {
        movieService.deleteMovie(movieId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addMovie(@RequestBody MovieDTO movie){
        String title = movie.title();
        String director = movie.director();
        String description = movie.description();
        int durationMin = movie.durationMin();
        Set<Actor> actors = movie.actors();

        Movie movieToAdd = movieService.pushMovie(new Movie(title, director, description, durationMin, actors));
        String movieURI = String.format("/movies/%d", movieToAdd.getId());

        return ResponseEntity.created(URI.create(movieURI)).build();
    }

    @RequestMapping(value = "/{movieId}", method = RequestMethod.PATCH)
    public ResponseEntity<Void> changeMovie(@PathVariable Long movieId, @RequestBody MovieDTO movie){
        String title = movie.title();
        String director = movie.director();
        String description = movie.description();
        int durationMin = movie.durationMin();
        Set<Actor> actors = movie.actors();

        try{
            movieService.updateMovie(movieId, new Movie(title, director, description, durationMin, actors));
            return ResponseEntity.noContent().build();
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }
}
