package by.ryni.share;

import by.ryni.share.dto.AbstractDto;
import by.ryni.share.entity.AbstractEntity;
import by.ryni.share.exception.RepositoryException;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.mapper.GenericMapper;
import by.ryni.share.api.GenericRepository;
import by.ryni.share.api.GenericService;
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
    public void save(D dto) throws ServiceException {
        try {
            E mappedEntity = mapper.dtoToEntity(dto);
            repository.save(mappedEntity);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository exception " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public void delete(int id) throws ServiceException {
        try {
            if (!repository.getById(id).isPresent()) {
                throw new ServiceException("Entity does not exists");
            }
            repository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException("Repository exception " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Optional<D> update(D dto) throws ServiceException {
        Optional<D> updatedDto;
        try {
            if (!repository.getById(dto.getId()).isPresent()) {
                throw new ServiceException("Entity does not exists");
            }
            repository.update(mapper.dtoToEntity(dto, repository.getById(dto.getId()).get()));
            updatedDto = repository.getById(dto.getId()).map(e -> mapper.entityToDto(e));
        } catch (RepositoryException e) {
            throw new ServiceException("Repository exception " + e.getMessage());
        }
        return updatedDto;
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
        for (E entity: repository.getAll()) {
            list.add(mapper.entityToDto(entity));
        }
        return list;
    }
}
