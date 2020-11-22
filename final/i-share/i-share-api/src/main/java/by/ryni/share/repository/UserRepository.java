package by.ryni.share.repository;

import by.ryni.share.entity.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User> {
    Optional<User> findByUsername(String username);
}
