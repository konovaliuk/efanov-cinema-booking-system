package com.application.dao.jpa;

import com.application.dao.MovieDao;
import com.application.model.Actor;
import com.application.model.Movie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;
import java.util.function.Consumer;

public class JPAMovieDao implements MovieDao {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("Cinema").createEntityManager();
    @Override
    public Movie get(long id) {
        Movie movie = entityManager.find(Movie.class, id);
        if(movie != null)
            return movie;
        else
            return null;
    }

    @Override
    public List<Movie> getAll() {
        Query query = entityManager.createQuery("SELECT E FROM Movie E");
        return query.getResultList();
    }

    @Override
    public void save(Movie movie) {
        executeInsideTransaction(entityManager -> entityManager.persist(movie));
    }

    @Override
    public void update(Movie movie) {
        executeInsideTransaction(entityManager -> entityManager.merge(movie));
    }

    @Override
    public void delete(long id) {
        Movie movie = entityManager.getReference(Movie.class, id);
        if(movie != null)
            executeInsideTransaction(entityManager -> entityManager.remove(movie));
    }

    @Override
    public List<Movie> getFilmsByDirector(String director) {
        Query query = entityManager.createQuery("SELECT E FROM Movie E WHERE E.director = :director");
        query.setParameter("director", director);
        return query.getResultList();
    }
    @Override
    public List<Actor> getFilmActors(long filmId) {
        Movie movie = entityManager.find(Movie.class, filmId);
        if(movie != null)
            return movie.getActors().stream().toList();
        else
            return null;
    }
    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
