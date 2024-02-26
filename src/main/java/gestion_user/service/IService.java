package gestion_user.service;

import gestion_user.entities.User;

import java.util.List;

public interface IService<C> {

   void addAdherent(C c);

    void deleteAdherent(int id);

    void updateAdherent(int id, C c);

    List<C> readAllAdherent();

    User readAdherentById(int id);

    //C readAdherentById(int id);
}