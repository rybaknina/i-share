package eu.senla.course.service;

import eu.senla.course.api.*;

public class ManagerProvider {

    private final static ManagerProvider instance = new ManagerProvider();
    private final IGarage garageManager = new GarageManager();
    private final IMechanic mechanicManager = new MechanicManager();
    private final IOrder orderManager = new OrderManager();
    private final IService serviceManager = new ServiceManager();
    private final ISpot spotManager = new SpotManager();

    private ManagerProvider(){
    }
    public static ManagerProvider getInstance(){
        return instance;
    }
    public IGarage getGarageManager(){
        return instance.garageManager;
    }
    public IMechanic getMechanicManager(){
        return instance.mechanicManager;
    }
    public IOrder getOrderManager(){
        return instance.orderManager;
    }
    public IService getServiceManager(){
        return instance.serviceManager;
    }
    public ISpot getSpotManager(){
        return instance.spotManager;
    }
}
