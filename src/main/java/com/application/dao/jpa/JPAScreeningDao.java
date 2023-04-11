package com.application.dao.jpa;

import com.application.dao.ScreeningDao;
import com.application.model.Movie;
import com.application.model.Screening;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;
import java.util.function.Consumer;

public class JPAScreeningDao implements ScreeningDao {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("Cinema").createEntityManager();
    @Override
    public Screening get(long id) {
        Screening screening = entityManager.find(Screening.class, id);
        if(screening != null)
            return screening;
        else
            return null;
    }

    @Override
    public List<Screening> getAll() {
        Query query = entityManager.createQuery("SELECT E FROM Screening E");
        return query.getResultList();
    }

    @Override
    public void save(Screening screening) {
        executeInsideTransaction(entityManager -> entityManager.persist(screening));
    }

    @Override
    public void update(Screening screening) {
        executeInsideTransaction(entityManager -> entityManager.merge(screening));
    }

    @Override
    public void delete(long id) {
        Screening screening = entityManager.getReference(Screening.class, id);
        if(screening != null)
            executeInsideTransaction(entityManager -> entityManager.remove(screening));
    }

    @Override
    public Movie getFilmScreening(long filmId) {
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
