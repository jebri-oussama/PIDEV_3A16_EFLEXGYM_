package org.example.Gestion_planing.service;

import java.util.List;

public interface IntService<T> {
    void add(T t);

    void delete(T t);

    void update(T t);

    List<T> readAll();

    T readById(int id);
}
