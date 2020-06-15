package eu.senla.course.controller;

import eu.senla.course.api.IToolService;
import eu.senla.course.entity.Tool;

import java.util.List;

public class ToolController {
    private final IToolService service;

    public ToolController(IToolService service) {
        this.service = service;
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
