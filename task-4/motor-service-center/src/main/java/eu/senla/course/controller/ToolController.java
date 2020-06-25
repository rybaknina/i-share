package eu.senla.course.controller;

import eu.senla.course.api.IToolService;
import eu.senla.course.entity.Tool;
import eu.senla.course.exception.ServiceException;
import eu.senla.course.service.ToolService;

import java.util.List;

public class ToolController {

    private final static ToolController instance = new ToolController();
    private final IToolService service = ToolService.getInstance();

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
    public Tool getToolById(int id) throws ServiceException {
        return service.getToolById(id);
    }

    public void deleteTool(Tool tool) throws ServiceException {
        service.deleteTool(tool);
    }
    public Tool getToolByName(String name) throws ServiceException {
        return service.getToolByName(name);
    }
    public void updateTool(Tool tool) throws ServiceException{
        service.updateTool(tool);
    }
    public void toolsFromCsv() throws ServiceException{
        service.toolsFromCsv();
    }
    public void toolsToCsv() throws ServiceException{
        service.toolsToCsv();
    }
}
