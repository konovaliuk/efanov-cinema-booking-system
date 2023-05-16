package com.example.cinemabookingsystem.Repository;

import com.example.cinemabookingsystem.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie, Long> {
}
