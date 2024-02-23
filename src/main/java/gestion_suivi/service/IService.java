package gestion_suivi.service;

import gestion_suivi.entitis.User;

import java.util.List;

public interface IService <T>{
    public void add (T t);
    public void delete (T t);
    public void update (T t);
    public List<T> readAll();
    public T readById(int id);
}
