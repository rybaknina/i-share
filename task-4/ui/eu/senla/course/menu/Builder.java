package eu.senla.course.menu;

import eu.senla.course.action.garage.AddGarageAction;
import eu.senla.course.menu.constant.GarageMenu;
import eu.senla.course.menu.constant.MainMenu;
import eu.senla.course.action.service.AddServiceAction;
import eu.senla.course.menu.constant.ServiceMenu;

public class Builder {

    private final static Builder instance = new Builder();

    private Menu serviceMenu = new Menu(MainMenu.SUB.getName());
    private Menu garageMenu = new Menu(MainMenu.SUB.getName());
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

        rootMenu.add(new MenuItem(GarageMenu.GARAGE.getName(), garageMenu));
        rootMenu.add(new MenuItem(ServiceMenu.SERVICE.getName(), serviceMenu));

        // TODO: All actions and other menu items
        rootMenu.add(exitItem);

        return rootMenu;
    }
    public Menu getRootMenu(){
        return this.buildMenu();
    }
}
