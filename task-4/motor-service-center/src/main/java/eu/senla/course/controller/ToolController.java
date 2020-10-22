package eu.senla.course.controller;

import eu.senla.course.api.service.IToolService;
import eu.senla.course.dto.tool.ToolDto;
import eu.senla.course.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ToolController {

    private IToolService service;
    private static ToolController instance;

    public static ToolController getInstance() {
        if (instance == null) {
            instance = new ToolController();
        }
        return instance;
    }

    @Autowired
    @Qualifier("toolService")
    public void setService(IToolService service) {
        this.service = service;
    }

    @Autowired
    public void setController(ToolController controller) {
        this.instance = controller;
    }

    @PostMapping("/tools")
    public void addTool(@RequestBody ToolDto toolDto) throws ServiceException {
        service.addTool(toolDto);
    }

    @GetMapping("/tools")
    public List<ToolDto> getTools() {
        return service.getTools();
    }

    @PatchMapping("/tools")
    public void setTools(@PathVariable List<ToolDto> toolDtoList) {
        service.setTools(toolDtoList);
    }

    @GetMapping("/tools/{id}")
    public ToolDto getToolById(@PathVariable int id) {
        return service.getToolById(id);
    }

    @PreAuthorize("hasAuthority('delete')")
    @DeleteMapping("/tools/{id}")
    public void deleteTool(@PathVariable int id) {
        service.deleteTool(id);
    }

    @PreAuthorize("hasAnyAuthority('delete', 'write')")
    @PutMapping("/tools")
    public void updateTool(@RequestBody ToolDto toolDto) throws ServiceException {
        service.updateTool(toolDto);
    }

    public void toolsFromCsv() throws ServiceException {
        service.toolsFromCsv();
    }

    public void toolsToCsv() {
        service.toolsToCsv();
    }
}
