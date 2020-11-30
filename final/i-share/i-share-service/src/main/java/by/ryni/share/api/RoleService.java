package by.ryni.share.api;

import by.ryni.share.dto.RoleDto;
import by.ryni.share.exception.ServiceException;

public interface RoleService extends GenericService<RoleDto> {
    RoleDto findByName(String name) throws ServiceException;
}
