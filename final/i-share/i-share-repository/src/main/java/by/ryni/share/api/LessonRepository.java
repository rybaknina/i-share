package by.ryni.share.api;

import by.ryni.share.criteria.SearchCriteria;
import by.ryni.share.entity.Lesson;

import java.util.List;

public interface LessonRepository extends GenericRepository<Lesson> {
    List<Lesson> search(List<SearchCriteria> params);
}
