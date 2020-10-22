package eu.senla.course.api.repository;

import eu.senla.course.entity.Role;

public interface IRoleRepository extends IRepository<Role> {
    Role findByName(String name);
}
