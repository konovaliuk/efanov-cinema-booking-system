package com.application.dao.jpa;

import com.application.dao.UserDao;
import com.application.dao.util.PasswordEncoder;
import com.application.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;
import java.util.function.Consumer;

public class JPAUserDao implements UserDao {

    private EntityManager entityManager = Persistence.createEntityManagerFactory("Cinema").createEntityManager();

    @Override
    public User get(long id) {
        User user = entityManager.find(User.class, id);
        if(user != null)
            return user;
        else
            return null;
    }

    @Override
    public List<User> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM User e");
        return query.getResultList();
    }

    @Override
    public void save(User user) {
        String password = PasswordEncoder.encodePassword(user.getEmail(), user.getPassword());
        user.setPassword(password);
        executeInsideTransaction(entityManager -> entityManager.persist(user));
    }

    @Override
    public void update(User user) {
        executeInsideTransaction(entityManager -> entityManager.merge(user));
    }

    @Override
    public void delete(long id) {
        User user = entityManager.getReference(User.class, id);
        if(user != null)
            executeInsideTransaction(entityManager -> entityManager.remove(user));
    }

    @Override
    public User getByEmail(String email) {
        Query query = entityManager.createQuery("SELECT e FROM User e WHERE e.email = :email");
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }

    @Override
    public void makeAdmin(long id) {
        executeInsideTransaction(entityManager -> {
            Query query = entityManager.createQuery("UPDATE User E set E.isAdmin = true WHERE E.id = :id");
            query.setParameter("id", id);
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
