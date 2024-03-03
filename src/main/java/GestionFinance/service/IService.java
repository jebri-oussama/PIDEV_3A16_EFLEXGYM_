package GestionFinance.service;

import GestionFinance.entites.Abonnement;

import java.util.List;

public interface IService<T> {

    void add(T t);

    void delete(T t);

    void update(T t);

    List<T> readAll();

    T readById(int id);

    Abonnement readAbonnementForLoggedInUser();

    List<Abonnement> readByUserId(int userId);
}