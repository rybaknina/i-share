package eu.senla.course.api.service;

import eu.senla.course.dto.permission.PermissionDto;

public interface IPermissionService {
    PermissionDto loadRoleByName(String name);
}
