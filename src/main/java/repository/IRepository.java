package repository;

import java.util.List;

public interface IRepository<E> {


    public E getOne(E e);

    public List<E> getAll();

    public int size();

    E add(E obj);

    void delete(int id);
}