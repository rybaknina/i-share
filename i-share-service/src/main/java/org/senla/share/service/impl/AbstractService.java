package org.senla.share.service.impl;

import org.senla.share.dto.AbstractDto;
import org.senla.share.entity.AbstractEntity;
import org.senla.share.mapper.GenericMapper;
import org.senla.share.repository.GenericRepository;
import org.senla.share.service.GenericService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<D extends AbstractDto, E extends AbstractEntity, R extends GenericRepository<E>> implements GenericService<D> {
    private R repository;
    private GenericMapper<D, E> mapper;

    public AbstractService(R repository, GenericMapper<D, E> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Optional<D> save(D dto) {
        E mappedEntity = mapper.dtoToEntity(dto);
        Optional<E> entity = repository.save(mappedEntity);
        return Optional.ofNullable(mapper.entityToDto(entity.get()));
    }

    @Transactional
    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Transactional
    @Override
    public Optional<D> update(D dto) {
        Optional<E> entity = repository.getById(dto.getId());

        if (entity.isPresent()) {
            E convertedEntity = mapper.dtoToEntity(dto, entity.get());
            return Optional.of(mapper.entityToDto(repository.update(convertedEntity).get()));
        }
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<D> getById(int id) {
        Optional<E> entity = repository.getById(id);
        return entity.map(e -> mapper.entityToDto(e));
    }

    @Transactional(readOnly = true)
    @Override
    public List<D> getAll() {
        List<D> list = new ArrayList<>();
        for (E entity : repository.getAll()) {
            list.add(mapper.entityToDto(entity));
        }
        return list;
    }
}
