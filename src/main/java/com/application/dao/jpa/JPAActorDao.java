package com.application.dao.jpa;

import com.application.dao.ActorDao;
import com.application.model.Actor;
import com.application.model.Movie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;
import java.util.function.Consumer;

public class JPAActorDao implements ActorDao {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("Cinema").createEntityManager();
    @Override
    public Actor get(long id) {
        Actor actor = entityManager.find(Actor.class, id);
        if(actor != null)
            return actor;
        else
            return null;
    }

    @Override
    public List<Actor> getAll() {
        Query query = entityManager.createQuery("SELECT E FROM Actor E");
        return query.getResultList();
    }
    @Override
    public void save(Actor actor) {
        executeInsideTransaction(entityManager -> entityManager.persist(actor));
    }
    @Override
    public void update(Actor actor) {
        executeInsideTransaction(entityManager -> entityManager.merge(actor));
    }
    @Override
    public void delete(long id) {
        Actor actor = entityManager.getReference(Actor.class, id);
        if(actor != null)
            executeInsideTransaction(entityManager -> entityManager.remove(actor));
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

    @Override
    public List<Movie> getActorFilms(long id) {
        return null;
    }
}
