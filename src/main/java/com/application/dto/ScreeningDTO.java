package com.application.dto;

import com.application.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScreeningDTO {
    private Movie movie;
    private java.sql.Timestamp screeningStart;
}
