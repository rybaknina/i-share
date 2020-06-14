package eu.senla.course.api;

import eu.senla.course.entity.Tool;

import java.util.List;

public interface IToolService {
    void addService(Tool service);
    List<Tool> getServices();
    void setServices(List<Tool> services);
}
