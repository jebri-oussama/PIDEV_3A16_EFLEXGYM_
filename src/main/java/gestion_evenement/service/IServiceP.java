package gestion_evenement.service;

import gestion_evenement.entities.Participation;

import java.util.List;

public interface IServiceP<P> {
    void add(Participation p);

    void delete(int id);

    void update(int id, Participation participation);

    List<Participation> readAll();

    Participation readById(int id);

    void addParticipation(int id,int userId);
}
