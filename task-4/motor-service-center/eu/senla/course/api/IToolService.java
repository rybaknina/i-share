package eu.senla.course.api;

import eu.senla.course.entity.Tool;

import java.util.List;

public interface IToolService {
    void addTool(Tool tool);
    List<Tool> getTools();
    void setTools(List<Tool> tools);
    Tool getToolById(int id);
    void deleteTool(Tool tool);
    Tool getToolByName(String name);
}
