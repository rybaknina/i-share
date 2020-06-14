package eu.senla.course.service;

import eu.senla.course.api.IToolService;
import eu.senla.course.entity.Tool;

import java.util.ArrayList;
import java.util.List;

public class ToolService implements IToolService {
    private List<Tool> services;

    public ToolService() {
        this.services = new ArrayList<>();
    }

    public List<Tool> getServices() {
        return services;
    }

    public void setServices(List<Tool> services) {
        this.services = services;
    }

    public void addService(Tool service){
        services.add(service);
    }

    public Tool getSerbiceBiId(int id){
        if (services == null){
            System.out.println("Services is not exist");
            return null;
        }
        return services.get(id);
    }

    public void deleteService(Tool service){
        services.removeIf(e -> e.equals(service));
    }

    public Tool getServiceByName(String name){
        for (Tool service: services){
            if (service.getName() == name){
                return service;
            }
        }
        System.out.println("Tool is not found");
        return null;
    }
}
