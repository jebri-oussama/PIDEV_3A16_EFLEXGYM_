package gestion_user.service;

import gestion_user.entities.User;

import java.util.List;

public interface IService<C> {

    void add(C c);

    void delete(int id);

    void update(int id, C c);

    List<C> readAll();

    void addAdherent(User c);

    void updateAdherent(int id, User c);

    List<User> readAllAdherent();

    void deleteAdherent(int id);

    User readById(int id);

    C readAdherentById(int id);
}