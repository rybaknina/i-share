package eu.senla.course.service;

import eu.senla.course.api.*;

public class ServiceProvider {

    private final static ServiceProvider instance = new ServiceProvider();
    private final IGarageService garageManager = new GarageService();
    private final IMechanicService mechanicManager = new MechanicService();
    private final IOrderService orderManager = new OrderService();
    private final IToolService serviceManager = new ToolService();
    private final ISpotService spotManager = new SpotService();

    private ServiceProvider(){
    }
    public static ServiceProvider getInstance(){
        return instance;
    }
    public IGarageService getGarageManager(){
        return instance.garageManager;
    }
    public IMechanicService getMechanicManager(){
        return instance.mechanicManager;
    }
    public IOrderService getOrderManager(){
        return instance.orderManager;
    }
    public IToolService getServiceManager(){
        return instance.serviceManager;
    }
    public ISpotService getSpotManager(){
        return instance.spotManager;
    }
}
