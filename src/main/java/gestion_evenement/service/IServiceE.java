package gestion_evenement.service;

import gestion_evenement.entities.Evenement;

import java.util.List;

public interface IServiceE<E> {

    void add(E t);

    void delete(int id);

    void update(int id, Evenement evenement);

    List<E> readAll();
    //
    E readById(int id);

}