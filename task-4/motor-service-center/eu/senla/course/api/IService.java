package eu.senla.course.api;

import eu.senla.course.entity.Service;

import java.util.List;

public interface IService {
    void addService(Service service);
    List<Service> getServices();
    void setServices(List<Service> services);
}
