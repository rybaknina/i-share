package eu.senla.course.controller;

import eu.senla.course.entity.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceManager {
    private static final int MAX_NUMBER_OF_SERVICES = 5;
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
    public int lengthServices(){
        return MAX_NUMBER_OF_SERVICES;
    }
    public void addService(Service service){
        if (services.size() == MAX_NUMBER_OF_SERVICES) {
            System.out.println("Services is over");
        }
        else {
            services.add(service);
        }
    }

    public Service getSerbiceBiId(int id){
        return (services == null)? null: services.get(id);
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
        return null;
    }
}
