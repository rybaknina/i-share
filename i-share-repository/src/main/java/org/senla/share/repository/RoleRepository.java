package org.senla.share.repository;

import org.senla.share.entity.Role;

import java.util.Optional;

public interface RoleRepository extends GenericRepository<Role> {
    Optional<Role> findByName(String name);
}
