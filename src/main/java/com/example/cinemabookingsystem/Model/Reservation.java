package com.example.cinemabookingsystem.Model;

import ch.qos.logback.core.joran.event.BodyEvent;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
    @ManyToOne
    @JoinColumn(name = "screening_id")
    @JsonManagedReference
    private Screening screening;
    private Boolean reserved;
    private Boolean active;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<SeatReserved> bookedSeats;

    public Reservation(User user, Screening screening, Boolean reserved, Boolean active, Set<SeatReserved> bookedSeats){
        this.user = user;
        this.screening = screening;
        this.reserved = reserved;
        this.active = active;
        this.bookedSeats = bookedSeats;
    }
}
