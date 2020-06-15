package eu.senla.course.service;

import eu.senla.course.api.*;

public class ServiceProvider {

    private final static ServiceProvider instance = new ServiceProvider();
    private final IGarageService garageService = new GarageService();
    private final IMechanicService mechanicService = new MechanicService();
    private final IOrderService orderService = new OrderService();
    private final IToolService toolService = new ToolService();
    private final ISpotService spotService = new SpotService();

    private ServiceProvider(){
    }
    public static ServiceProvider getInstance(){
        return instance;
    }
    public IGarageService getGarageService(){
        return instance.garageService;
    }
    public IMechanicService getMechanicService(){
        return instance.mechanicService;
    }
    public IOrderService getOrderService(){
        return instance.orderService;
    }
    public IToolService getToolService(){
        return instance.toolService;
    }
    public ISpotService getSpotService(){
        return instance.spotService;
    }
}
