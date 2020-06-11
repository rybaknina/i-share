package eu.senla.course.controller;

public class ManagerProvider {

    private final static ManagerProvider instance = new ManagerProvider();
    private final GarageManager garageManager = new GarageManager();
    private final MechanicManager mechanicManager = new MechanicManager();
    private final OrderManager orderManager = new OrderManager();
    private final ServiceManager serviceManager = new ServiceManager();
    private final SpotManager spotManager = new SpotManager();
    private final BillService billService = new BillService();

    private ManagerProvider(){
    }
    public static ManagerProvider getInstance(){
        return instance;
    }
    public GarageManager getGarageManager(){
        return instance.garageManager;
    }
    public MechanicManager getMechanicManager(){
        return instance.mechanicManager;
    }
    public OrderManager getOrderManager(){
        return instance.orderManager;
    }
    public ServiceManager getServiceManager(){
        return instance.serviceManager;
    }
    public SpotManager getSpotManager(){
        return instance.spotManager;
    }
    public BillService getBillService(){
        return instance.billService;
    }
}
