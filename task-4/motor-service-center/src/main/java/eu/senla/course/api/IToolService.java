package eu.senla.course.api;

import eu.senla.course.entity.Tool;
import eu.senla.course.exception.ServiceException;

import java.util.List;

public interface IToolService {
    void addTool(Tool tool) throws ServiceException;
    List<Tool> getTools();
    void setTools(List<Tool> tools);
    Tool getToolById(int id) throws ServiceException;
    void deleteTool(Tool tool) throws ServiceException;
    Tool getToolByName(String name) throws ServiceException;
    void updateTool(Tool tool) throws ServiceException;
    void toolsFromCsv() throws ServiceException;
    void toolsToCsv() throws ServiceException;
}