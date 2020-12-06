package org.senla.share.repository;

import org.senla.share.entity.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User> {
    Optional<User> findByUsername(String username);
}
