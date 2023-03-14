package com.application.dao;

import com.application.model.Actor;
import com.application.model.Movie;

import java.util.ArrayList;
import java.util.List;

public interface MovieDao extends Dao<Movie> {
    List<Movie> getFilmsByDirector(String director);
    List<Actor> getFilmActors(long filmId);
}
