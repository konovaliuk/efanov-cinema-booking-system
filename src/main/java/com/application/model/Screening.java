package com.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "screening")
public class Screening implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_id")
    private long ID;
    @ManyToOne(targetEntity = Movie.class)
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @Column(name = "screening_start")
    private java.sql.Timestamp screeningStart;
}
