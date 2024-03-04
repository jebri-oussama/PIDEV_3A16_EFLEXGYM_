package gestion_suivi.service;

import java.util.List;

public interface IExerciceService <T>{
    public boolean add (T t);
    public boolean delete (T t);

    public void update (T t);
    public List<T> readAll();
    public T readById(int id);
}
