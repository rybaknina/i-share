package by.ryni.share.api;

import by.ryni.share.criteria.SearchCriteria;
import by.ryni.share.entity.Course;

import java.util.List;

public interface CourseRepository extends GenericRepository<Course> {
    List<Course> search(List<SearchCriteria> params);
    int availableSpace(int id);
}
