package com.application.dao;

import com.application.model.User;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    T get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(long id);
}