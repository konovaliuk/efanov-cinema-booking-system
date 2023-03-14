package com.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieActor {
    private long actorId;
    private long movieId;
}
