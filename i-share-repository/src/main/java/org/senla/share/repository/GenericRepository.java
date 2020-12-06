package org.senla.share.repository;

import org.senla.share.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<E extends AbstractEntity> {

    Optional<E> save(E entity);

    void delete(int id);

    Optional<E> update(E entity);

    Optional<E> getById(int id);

    List<E> getAll();
}
