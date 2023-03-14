package com.application.dao;

import com.application.model.Movie;
import com.application.model.Screening;

public interface ScreeningDao extends Dao<Screening>{
    Movie getFilmScreening(long filmId);
}
