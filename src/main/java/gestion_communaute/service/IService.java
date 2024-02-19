package gestion_communaute.service;

import gestion_communaute.entities.Reclamation;

import java.util.List;

public interface IService<R> {

    void add(R t);

    void delete(int id);

    void update(R t);

    List<R> readAll();

    R readById(int id);

    // Method to retrieve reclamations by status
    List<R> getReclamationsByStatusAndType(Reclamation.Status status, String type);
}
