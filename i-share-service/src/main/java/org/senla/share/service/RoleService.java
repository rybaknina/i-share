package org.senla.share.service;

import org.senla.share.dto.RoleDto;

import java.util.Optional;

public interface RoleService extends GenericService<RoleDto> {
    Optional<RoleDto> findByName(String name);
}
