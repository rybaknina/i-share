package eu.senla.course.menu;

import eu.senla.course.api.IAction;

import java.io.IOException;


public class MenuItem {
    private String title;
    private Menu nextMenu;
    private IAction action;


    public MenuItem(String title) {
        this.title = title;
    }

    public MenuItem(String title, Menu nextMenu) {
        this.title = title;
        this.nextMenu = nextMenu;
    }

    public MenuItem(String title, Menu nextMenu, IAction action) {
        this.title = title;
        this.nextMenu = nextMenu;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public IAction getAction() {
        return action;
    }

    public void setAction(IAction action) {
        this.action = action;
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    public void setNextMenu(Menu nextMenu) {
        this.nextMenu = nextMenu;
    }

    public void doAction() throws IOException {
        action.execute();
    }

    @Override
    public String toString() {
        return title;
    }
}
