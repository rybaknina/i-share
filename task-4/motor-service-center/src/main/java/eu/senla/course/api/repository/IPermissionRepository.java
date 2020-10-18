package eu.senla.course.api.repository;

import eu.senla.course.entity.Permission;

public interface IPermissionRepository extends IRepository<Permission> {
    Permission findByName(String name);
}
