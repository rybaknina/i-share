package eu.senla.course.menu;

import eu.senla.course.action.garage.AddGarageAction;
import eu.senla.course.action.mechanic.AddMechanicAction;
import eu.senla.course.action.order.AddOrderAction;
import eu.senla.course.entity.Mechanic;
import eu.senla.course.menu.constant.*;
import eu.senla.course.action.service.AddServiceAction;

public class Builder {

    private final static Builder instance = new Builder();

    private Menu serviceMenu = new Menu(MainMenu.SUB.getName());
    private Menu garageMenu = new Menu(MainMenu.SUB.getName());
    private Menu orderMenu = new Menu(MainMenu.SUB.getName());
    private Menu mechanicMenu = new Menu(MainMenu.SUB.getName());
    private Menu rootMenu = new Menu(MainMenu.ROOT.getName());


    private Builder() {
    }

    public static Builder getInstance(){
        return instance;
    }

    private Menu buildMenu(){

        MenuItem mainItem = new MenuItem(MainMenu.ROOT.getName(), rootMenu);
        MenuItem exitItem = new MenuItem(MainMenu.EXIT.getName());

        garageMenu.add(new MenuItem(GarageMenu.ADD.getName(), garageMenu, new AddGarageAction()));
        garageMenu.add(new MenuItem(MainMenu.RETURN.getName(), rootMenu));
        garageMenu.add(exitItem);
        mainItem.setNextMenu(garageMenu);

        serviceMenu.add(new MenuItem(ServiceMenu.ADD.getName(), serviceMenu, new AddServiceAction()));
        serviceMenu.add(new MenuItem(MainMenu.RETURN.getName(), rootMenu));
        serviceMenu.add(exitItem);
        mainItem.setNextMenu(serviceMenu);

        mechanicMenu.add(new MenuItem(MechanicMenu.ADD.getName(), mechanicMenu, new AddMechanicAction()));
        mechanicMenu.add(new MenuItem(MainMenu.RETURN.getName(), rootMenu));
        mechanicMenu.add(exitItem);
        mainItem.setNextMenu(mechanicMenu);

        orderMenu.add(new MenuItem(OrderMenu.ADD.getName(), orderMenu, new AddOrderAction()));
        orderMenu.add(new MenuItem(MainMenu.RETURN.getName(), rootMenu));
        orderMenu.add(exitItem);
        mainItem.setNextMenu(orderMenu);



        rootMenu.add(new MenuItem(GarageMenu.GARAGE.getName(), garageMenu));
        rootMenu.add(new MenuItem(ServiceMenu.SERVICE.getName(), serviceMenu));
        rootMenu.add(new MenuItem(MechanicMenu.MECHANIC.getName(), mechanicMenu));
        rootMenu.add(new MenuItem(OrderMenu.ORDER.getName(), orderMenu));

        // TODO: All actions and other menu items
        rootMenu.add(exitItem);

        return rootMenu;
    }
    public Menu getRootMenu(){
        return this.buildMenu();
    }
}
