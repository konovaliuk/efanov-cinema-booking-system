package com.application.dao.jpa;

import com.application.dao.ReservationDao;
import com.application.model.Reservation;
import com.application.model.Screening;
import com.application.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;
import java.util.function.Consumer;

public class JPAReservationDao implements ReservationDao {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("Cinema").createEntityManager();
    @Override
    public Reservation get(long id) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        if(reservation != null)
            return reservation;
        else
            return null;
    }

    @Override
    public List<Reservation> getAll() {
        Query query = entityManager.createQuery("SELECT E FROM Reservation E");
        return query.getResultList();
    }

    @Override
    public void save(Reservation reservation) {
        executeInsideTransaction(entityManager -> entityManager.persist(reservation));
    }

    @Override
    public void update(Reservation reservation) {
        executeInsideTransaction(entityManager -> entityManager.merge(reservation));
    }

    @Override
    public void delete(long id) {
        Reservation reservation = entityManager.getReference(Reservation.class, id);
        if(reservation != null)
            executeInsideTransaction(entityManager -> entityManager.remove(reservation));
    }

    @Override
    public User getUserByReservation(long userId) {
        return null;
    }

    @Override
    public Screening getScreeningByReservation(long screeningId) {
        return null;
    }

    @Override
    public void getPaid(long reservationId) {
        executeInsideTransaction(entityManager1 -> {
            Query query = entityManager.createQuery("UPDATE Reservation E SET e.paid = true WHERE E.id = :id");
            query.setParameter("id", reservationId);
            query.executeUpdate();
        });
    }

    @Override
    public void getReserved(long reservationId) {
        executeInsideTransaction(entityManager1 -> {
            Query query = entityManager.createQuery("UPDATE Reservation E SET e.reserved = true WHERE E.id = :id");
            query.setParameter("id", reservationId);
            query.executeUpdate();
        });
    }

    @Override
    public void activate(long reservationId) {
        executeInsideTransaction(entityManager1 -> {
            Query query = entityManager.createQuery("UPDATE Reservation E SET e.active = true WHERE E.id = :id");
            query.setParameter("id", reservationId);
            query.executeUpdate();
        });
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
