package com.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movie_actor")
public class MovieActor {
    private long actorId;
    private long movieId;
}
