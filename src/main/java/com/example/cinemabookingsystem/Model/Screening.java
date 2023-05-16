package com.example.cinemabookingsystem.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "screening")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonManagedReference
    private Movie movie;
    @Column(name = "screening_start")
    private Timestamp screeningStart;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<SeatReserved> seatReserved;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Reservation> reservations;

}
