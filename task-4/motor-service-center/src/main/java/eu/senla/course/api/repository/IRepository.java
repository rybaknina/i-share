package eu.senla.course.api.repository;

import eu.senla.course.exception.RepositoryException;

import java.util.List;

public interface IRepository<T> {
    void add(T t) throws RepositoryException;
    void delete(T t);
    void update(T t) throws RepositoryException;
    T getById(int id);
    List<T> getAll();
    void setAll(List<T> ts);
    void addAll(List<T> ts);
}
