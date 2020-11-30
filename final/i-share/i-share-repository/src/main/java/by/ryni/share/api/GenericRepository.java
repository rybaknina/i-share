package by.ryni.share.api;

import by.ryni.share.entity.AbstractEntity;
import by.ryni.share.exception.RepositoryException;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<E extends AbstractEntity> {

    Optional<E> save(E entity) throws RepositoryException;
    void delete(int id) throws RepositoryException;
    void update(E entity) throws RepositoryException;
    Optional<E> getById(int id);
    List<E> getAll();
}
