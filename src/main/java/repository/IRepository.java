package repository;

import model.Exercise;

import java.util.List;

public interface IRepository<E> {


    public E getOne(E e);

    public List<Exercise> getAll();

    public int size();

    E add(E obj);

    void delete(int id);
}