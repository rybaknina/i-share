package org.senla.share.repository;

import org.senla.share.criteria.SearchCriteria;
import org.senla.share.entity.Lesson;

import java.util.List;
import java.util.Set;

public interface LessonRepository extends GenericRepository<Lesson> {
    List<Lesson> search(List<SearchCriteria> params);

    Set<Lesson> lessonsByCourse(int id);
}
