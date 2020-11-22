package eu.senla.course.menu;

import java.io.IOException;
import java.util.List;

final public class Navigator {
    private final static Navigator instance = new Navigator();
    private Menu currentMenu;

    private Navigator() {

    }
    public static Navigator getInstance() {
        return instance;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu() {
        if (currentMenu != null) {
            System.out.println(currentMenu);
            List<MenuItem> items = currentMenu.getMenuItems();
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i));
            }
        } else {
            System.out.println("MainMenu is empty");
        }
    }

    public void navigate(Integer index) throws IOException {

        MenuItem item = currentMenu.getMenuItems().get(index);
        if (item != null && item.getAction() != null) {
            item.doAction();
        }
    }
}
