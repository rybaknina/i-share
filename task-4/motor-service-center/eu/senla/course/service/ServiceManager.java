package eu.senla.course.service;

import eu.senla.course.api.IService;
import eu.senla.course.entity.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceManager implements IService {
    private List<Service> services;

    public ServiceManager() {
        this.services = new ArrayList<>();
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addService(Service service){
        services.add(service);
    }

    public Service getSerbiceBiId(int id){
        if (services == null){
            System.out.println("Services is not exist");
            return null;
        }
        return services.get(id);
    }

    public void deleteService(Service service){
        services.removeIf(e -> e.equals(service));
    }

    public Service getServiceByName(String name){
        for (Service service: services){
            if (service.getName() == name){
                return service;
            }
        }
        System.out.println("Service is not found");
        return null;
    }
}
