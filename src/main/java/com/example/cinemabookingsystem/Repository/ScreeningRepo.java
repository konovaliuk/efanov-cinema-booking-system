package com.example.cinemabookingsystem.Repository;

import com.example.cinemabookingsystem.Model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningRepo extends JpaRepository<Screening, Long> {
}
