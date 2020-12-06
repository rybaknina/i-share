package org.senla.share.service;

import org.senla.share.criteria.SearchCriteria;
import org.senla.share.dto.CourseDto;
import org.senla.share.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface CourseService extends GenericService<CourseDto> {
    List<CourseDto> search(List<SearchCriteria> params);

    int availableSpace(int id);

    Boolean subscribe(int id, String username);

    Set<UserDto> membersByCourse(int id);
}
