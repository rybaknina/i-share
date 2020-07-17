package eu.senla.course.controller;

import eu.senla.course.annotation.di.Injection;
import eu.senla.course.api.service.IToolService;
import eu.senla.course.entity.Tool;
import eu.senla.course.exception.ServiceException;

import java.util.List;

public class ToolController {
    @Injection
    private IToolService service;
    private final static ToolController instance = new ToolController();

    private ToolController() {

    }

    public static ToolController getInstance(){
        return instance;
    }

    public void addTool(Tool tool) throws ServiceException {
        service.addTool(tool);
    }
    public List<Tool> getTools(){
        return service.getTools();
    }
    public void setTools(List<Tool> tools){
        service.setTools(tools);
    }
    public Tool getToolById(int id) {
        return service.getToolById(id);
    }

    public void deleteTool(Tool tool) throws ServiceException {
        service.deleteTool(tool);
    }

    public void updateTool(Tool tool) throws ServiceException{
        service.updateTool(tool);
    }
    public void toolsFromCsv() throws ServiceException{
        service.toolsFromCsv();
    }
    public void toolsToCsv(){
        service.toolsToCsv();
    }
}
