package by.ryni.share.service;

import by.ryni.share.dto.RoleDto;
import by.ryni.share.ecxeption.ServiceException;

public interface RoleService extends GenericService<RoleDto> {
    RoleDto findByName(String name) throws ServiceException;
}
