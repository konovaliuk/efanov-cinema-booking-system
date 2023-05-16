package com.example.cinemabookingsystem.Repository;

import com.example.cinemabookingsystem.Model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepo extends JpaRepository<Actor, Long> {
}
