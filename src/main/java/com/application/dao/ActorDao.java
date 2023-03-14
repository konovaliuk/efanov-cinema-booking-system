package com.application.dao;

import com.application.model.Actor;
import com.application.model.Movie;

import java.util.List;

public interface ActorDao extends Dao<Actor>{
    List<Movie> getActorFilms(long id);
}
