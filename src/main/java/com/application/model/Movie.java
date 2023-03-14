package com.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Movie implements Serializable {
    private final long ID;
    private String title;
    private String director;
    private String description;
    private int durationTime;
}
