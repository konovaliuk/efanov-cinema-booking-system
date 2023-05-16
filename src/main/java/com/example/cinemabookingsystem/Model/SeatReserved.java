package com.example.cinemabookingsystem.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "seat_reserved")
public class SeatReserved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_reserved_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    @JsonManagedReference
    private Reservation reservation;
    @ManyToOne
    @JoinColumn(name = "screening_id")
    @JsonManagedReference
    private Screening screening;
    @ManyToOne
    @JoinColumn(name = "seat_id")
    @JsonManagedReference
    private Seat seat;
}
