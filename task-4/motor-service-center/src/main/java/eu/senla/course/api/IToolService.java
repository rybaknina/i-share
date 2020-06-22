package eu.senla.course.api;

import eu.senla.course.entity.Tool;
import eu.senla.course.service.ServiceException;

import java.util.List;

public interface IToolService {
    void addTool(Tool tool);
    List<Tool> getTools();
    void setTools(List<Tool> tools);
    Tool getToolById(int id);
    void deleteTool(Tool tool);
    Tool getToolByName(String name);
    void updateTool(int id, Tool tool) throws ServiceException;
    void toolsFromCsv() throws ServiceException;
    void toolsToCsv() throws ServiceException;
}