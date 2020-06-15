package eu.senla.course.service;

import eu.senla.course.api.IToolService;
import eu.senla.course.entity.Tool;

import java.util.ArrayList;
import java.util.List;

public class ToolService implements IToolService {
    private List<Tool> tools;

    public ToolService() {
        this.tools = new ArrayList<>();
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public void addTool(Tool tool){
        tools.add(tool);
    }

    public Tool getToolBiId(int id){
        if (tools == null){
            System.out.println("Tools are not exist");
            return null;
        }
        return tools.get(id);
    }

    public void deleteTool(Tool tool){
        tools.removeIf(e -> e.equals(tool));
    }

    public Tool getToolByName(String name){
        for (Tool tool: tools){
            if (tool.getName() == name){
                return tool;
            }
        }
        System.out.println("Tool is not found");
        return null;
    }
}
