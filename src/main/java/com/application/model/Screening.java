package com.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Screening implements Serializable {
    private final long ID;
    private Movie movie;
    private java.sql.Timestamp screeningStart;
}
