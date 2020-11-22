package by.ryni.share.repository;

import by.ryni.share.entity.Role;

import java.util.Optional;

public interface RoleRepository extends GenericRepository<Role> {
    Optional<Role> findByName(String name);
}
