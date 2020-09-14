package eu.senla.course.controller;

import eu.senla.course.api.service.IToolService;
import eu.senla.course.entity.Tool;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
final public class ToolController {

    private IToolService service;
    private static ToolController instance;

    private ToolController() {
    }

    public static ToolController getInstance() {
        if (instance == null) {
            instance = new ToolController();
        }
        return instance;
    }

    @Autowired
    public void setService(IToolService service) {
        this.service = service;
    }

    @Autowired
    public void setController(ToolController controller) {
        this.instance = controller;
    }

    public void addTool(Tool tool) throws ServiceException {
        service.addTool(tool);
    }
    public List<Tool> getTools() {
        return service.getTools();
    }
    public void setTools(List<Tool> tools) {
        service.setTools(tools);
    }
    public Tool getToolById(int id) {
        return service.getToolById(id);
    }

    public void deleteTool(int id) {
        service.deleteTool(id);
    }

    public void updateTool(Tool tool) throws ServiceException {
        service.updateTool(tool);
    }
    public void toolsFromCsv() throws ServiceException {
        service.toolsFromCsv();
    }
    public void toolsToCsv() {
        service.toolsToCsv();
    }
}
