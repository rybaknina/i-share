package by.ryni.share.api;

import by.ryni.share.criteria.SearchCriteria;
import by.ryni.share.dto.LessonDto;

import java.util.List;

public interface LessonService extends GenericService<LessonDto> {
    List<LessonDto> search(List<SearchCriteria> params);
}
