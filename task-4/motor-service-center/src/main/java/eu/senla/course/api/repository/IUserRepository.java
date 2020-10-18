package eu.senla.course.api.repository;

import eu.senla.course.entity.User;

public interface IUserRepository extends IRepository<User> {
    User findByUsername(String username);
}
