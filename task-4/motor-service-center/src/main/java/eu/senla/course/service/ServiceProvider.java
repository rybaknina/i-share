package eu.senla.course.service;

import eu.senla.course.api.*;

public class ServiceProvider {

    private final static ServiceProvider instance = new ServiceProvider();
    private final IGarageService garageService = GarageService.getInstance();
    private final IMechanicService mechanicService = MechanicService.getInstance();
    private final IOrderService orderService = OrderService.getInstance();
    private final IToolService toolService = ToolService.getInstance();
    private final ISpotService spotService = SpotService.getInstance();

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
