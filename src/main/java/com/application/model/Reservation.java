package com.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Reservation implements Serializable {
    private final long ID;
    private User user;
    private Screening screening;
    private boolean reserved = false;
    private boolean paid = false;
    private boolean active = false;
}
