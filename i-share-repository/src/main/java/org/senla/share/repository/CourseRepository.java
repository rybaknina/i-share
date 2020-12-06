package org.senla.share.repository;

import org.senla.share.criteria.SearchCriteria;
import org.senla.share.entity.Course;
import org.senla.share.entity.User;

import java.util.List;
import java.util.Set;

public interface CourseRepository extends GenericRepository<Course> {
    List<Course> search(List<SearchCriteria> params);

    int availableSpace(int id);

    Set<User> membersByCourse(int id);
}
