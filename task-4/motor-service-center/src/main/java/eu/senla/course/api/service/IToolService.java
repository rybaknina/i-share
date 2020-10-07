package eu.senla.course.api.service;

import eu.senla.course.dto.tool.ToolDto;
import eu.senla.course.exception.ServiceException;

import java.util.List;

public interface IToolService {
    void addTool(ToolDto toolDto) throws ServiceException;
    List<ToolDto> getTools();
    void setTools(List<ToolDto> toolDtoList);
    ToolDto getToolById(int id);
    void deleteTool(int id);
    void updateTool(ToolDto toolDto) throws ServiceException;
    void toolsFromCsv() throws ServiceException;
    void toolsToCsv();
}