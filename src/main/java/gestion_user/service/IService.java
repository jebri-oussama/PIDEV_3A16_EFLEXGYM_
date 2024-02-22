package gestion_user.service;

import java.util.List;

public interface IService<C> {

   void addAdherent(C c);

    void deleteAdherent(int id);

    void updateAdherent(int id, C c);

    List<C> readAllAdherent();

    //C readAdherentById(int id);
}