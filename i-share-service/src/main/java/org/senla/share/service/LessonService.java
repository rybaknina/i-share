package org.senla.share.service;

import org.senla.share.criteria.SearchCriteria;
import org.senla.share.dto.LessonDto;

import java.util.List;
import java.util.Set;

public interface LessonService extends GenericService<LessonDto> {
    List<LessonDto> search(List<SearchCriteria> params);

    Set<LessonDto> lessonsByCourse(int id);
}
