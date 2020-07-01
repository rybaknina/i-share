package eu.senla.course.repository;

import eu.senla.course.api.IRepository;
import eu.senla.course.entity.Tool;
import eu.senla.course.exception.RepositoryException;

import java.util.ArrayList;
import java.util.List;

public class ToolRepository implements IRepository<Tool> {
    private List<Tool> tools;
    private static final ToolRepository instance = new ToolRepository();

    private ToolRepository(){
        tools = new ArrayList<>();
    }

    public static ToolRepository getInstance(){
        return instance;
    }

    @Override
    public void add(Tool tool) throws RepositoryException {
        if (tool == null){
            throw new RepositoryException("Tool is not exist");
        }
        tools.add(tool);
    }

    @Override
    public void delete(Tool tool) throws RepositoryException {
        if (tools.size() == 0 || tool == null){
            throw new RepositoryException("Tool is not found");
        }
        tools.removeIf(e -> e.equals(tool));
    }

    @Override
    public Tool getById(int id) throws RepositoryException {
        if (tools.size() <= id || tools.get(id) == null){
            throw new RepositoryException("Tool is not found");
        }
        return tools.get(id);
    }

    @Override
    public List<Tool> getAll() {
        return tools;
    }

    public void update(Tool tool) throws RepositoryException{
        int id = tools.indexOf(tool);
        if (id < 0){
            throw new RepositoryException("Tool is not found");
        }
        tools.set(id, tool);
    }
    public void setAll(List<Tool> tools){
        this.tools = tools;
    }
    public void addAll(List<Tool> tools){
        this.tools.addAll(tools);
    }
}
