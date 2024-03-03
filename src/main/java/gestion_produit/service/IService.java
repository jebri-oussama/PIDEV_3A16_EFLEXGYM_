package gestion_produit.service;

import gestion_produit.entities.panier;

import java.util.List;
public interface IService<T> {
    void add(T t);
    void delete(int id);

    void update( int id , T t);

    List<T> readAll();

    T readById(int id);


    panier getParticipationByUserId(int userID);

    panier getPanierByUserId(int userID);
}
