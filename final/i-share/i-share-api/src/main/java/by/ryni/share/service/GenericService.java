package by.ryni.share.service;

import by.ryni.share.dto.AbstractDto;
import by.ryni.share.ecxeption.ServiceException;

import java.util.List;
import java.util.Optional;

public interface GenericService<D extends AbstractDto> {
    void save(D dto) throws ServiceException;
    void delete(int id) throws ServiceException;
    Optional<D> update(D dto) throws ServiceException;
    Optional<D> getById(int id);
    List<D> getAll();
}
