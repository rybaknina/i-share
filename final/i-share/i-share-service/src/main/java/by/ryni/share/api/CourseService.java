package by.ryni.share.api;

import by.ryni.share.criteria.SearchCriteria;
import by.ryni.share.dto.CourseDto;
import by.ryni.share.exception.ServiceException;

import java.util.List;

public interface CourseService extends GenericService<CourseDto> {
    List<CourseDto> search(List<SearchCriteria> params);
    int availableSpace(int id);
    void subscribe(int id, String username) throws ServiceException;
}
