package eu.senla.course.controller;

import eu.senla.course.api.IToolService;
import eu.senla.course.entity.Tool;
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

    public void addTool(Tool tool){
        service.addTool(tool);
    }
    public List<Tool> getTools(){
        return service.getTools();
    }
    public void setTools(List<Tool> tools){
        service.setTools(tools);
    }
    public Tool getToolBiId(int id){
        return service.getToolBiId(id);
    }

    public void deleteTool(Tool tool){
        service.deleteTool(tool);
    }
    public Tool getToolByName(String name){
        return service.getToolByName(name);
    }
}
