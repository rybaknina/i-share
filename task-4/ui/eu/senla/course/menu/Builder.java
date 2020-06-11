package eu.senla.course.menu;

import eu.senla.course.action.MenuType;
import eu.senla.course.action.service.AddServiceAction;

public class Builder {
    private static final String ROOT_MENU = MenuType.ROOT_MENU.getName();
    private static final String SUB_MENU = MenuType.SUB_MENU.getName();

    private Menu serviceMenu = new Menu(SUB_MENU);
    private Menu rootMenu = new Menu(ROOT_MENU);


    public Builder() {
    }

    public eu.senla.course.menu.Menu buildMenu(){

        // TODO: All actions and check valid entered values

        serviceMenu.add(new MenuItem("Add service", serviceMenu, new AddServiceAction()));

        rootMenu.add(new MenuItem("Main menu", rootMenu));
        rootMenu.add(new MenuItem("Service menu", serviceMenu));


        return rootMenu;
    }
    public eu.senla.course.menu.Menu getRootMenu(){
        return this.buildMenu();
    }
}
