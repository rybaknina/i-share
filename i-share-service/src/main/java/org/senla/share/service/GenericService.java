package org.senla.share.service;

import org.senla.share.dto.AbstractDto;

import java.util.List;
import java.util.Optional;

public interface GenericService<D extends AbstractDto> {
    Optional<D> save(D dto);

    void delete(int id);

    Optional<D> update(D dto);

    Optional<D> getById(int id);

    List<D> getAll();
}
