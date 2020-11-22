package eu.senla.course.api.repository;

import eu.senla.course.entity.User;

import java.util.Optional;

public interface IUserRepository extends IRepository<User> {
    Optional<User> findByUsername(String username);
}
