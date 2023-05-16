package com.example.cinemabookingsystem.Service;

import com.example.cinemabookingsystem.Model.Actor;
import com.example.cinemabookingsystem.Repository.ActorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class ActorService {
    private final ActorRepo actorRepo;

    public List<Actor> getAllMovies(){
        return actorRepo.findAll();
    }

    public Actor getActorById(Long id) throws IllegalArgumentException{
        Optional<Actor> maybeActor = actorRepo.findById(id);
        if(maybeActor.isEmpty())
            throw new IllegalArgumentException("No such actor");
        return maybeActor.get();
    }

    public void updateActor(Long id, Actor actor) throws IllegalArgumentException {
        Optional<Actor> maybeActorToUpdate = actorRepo.findById(id);
        if(maybeActorToUpdate.isEmpty())
            throw new IllegalArgumentException("Invalid actor");

        Actor actorToUpdate = maybeActorToUpdate.get();
        actorToUpdate.setFirstName(actor.getFirstName());
        actorToUpdate.setLastName(actor.getLastName());
        actorToUpdate.setRole(actor.getRole());

        actorRepo.save(actorToUpdate);
    }

    public void deleteActor(Long id) {
        actorRepo.deleteById(id);
    }
}
