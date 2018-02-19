package repository;

import java.util.Set;

public interface IRepository<E> {


    public E getOne(E e);

    public Set<E> getAll();

    public int size();

    E add(E obj);

    void delete(int id);
}