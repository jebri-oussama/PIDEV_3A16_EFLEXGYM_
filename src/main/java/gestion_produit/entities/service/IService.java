package gestion_produit.entities.service;

import java.util.List;
public interface IService<T> {
    void add(T t);
    void delete(int id);

    void update( int id , T t);

    List<T> readAll();

     T readById(int id);


}
