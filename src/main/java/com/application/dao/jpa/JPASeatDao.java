package com.application.dao.jpa;

import com.application.dao.SeatDao;
import com.application.model.Seat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;
import java.util.function.Consumer;

public class JPASeatDao implements SeatDao {

    private EntityManager entityManager = Persistence.createEntityManagerFactory("Cinema").createEntityManager();
    @Override
    public Seat get(long id) {
        Seat seat = entityManager.find(Seat.class, id);
        if(seat != null)
            return seat;
        else
            return null;
    }

    @Override
    public List<Seat> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Seat e");
        return query.getResultList();
    }

    @Override
    public void save(Seat seat) {
        executeInsideTransaction(entityManager -> entityManager.persist(seat));
    }

    @Override
    public void update(Seat seat) {
        executeInsideTransaction(entityManager -> entityManager.merge(seat));
    }

    @Override
    public void delete(long id) {
        Seat seat = entityManager.getReference(Seat.class, id);
        if(seat != null)
            executeInsideTransaction(entityManager -> entityManager.remove(seat));
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
