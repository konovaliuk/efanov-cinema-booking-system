package com.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SeatReserved implements Serializable {
    private final long ID;
    private Seat seat;
    private Reservation reservation;
    private Screening screening;
}
