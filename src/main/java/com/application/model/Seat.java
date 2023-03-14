package com.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Seat implements Serializable {
    private final long ID;
    private int row;
    private int number;
}
