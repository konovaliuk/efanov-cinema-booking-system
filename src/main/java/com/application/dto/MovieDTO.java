package com.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class MovieDTO {
    private String title;
    private String director;
    private String description;
    private int durationTime;
}
