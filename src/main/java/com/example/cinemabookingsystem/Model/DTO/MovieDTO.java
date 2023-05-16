package com.example.cinemabookingsystem.Model.DTO;

import com.example.cinemabookingsystem.Model.Actor;

import java.util.Set;

public record MovieDTO (String title, String director, String description, int durationMin, Set<Actor> actors){
}
