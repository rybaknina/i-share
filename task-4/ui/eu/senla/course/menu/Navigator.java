package eu.senla.course.menu;

import java.util.List;

public class Navigator {
    private final static Navigator instance = new Navigator();
    private Menu currentMenu;

    private Navigator(){

    }
    public static Navigator getInstance(){
        return instance;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu(){
        if (currentMenu != null) {
            System.out.println(currentMenu);
            List<MenuItem> items = currentMenu.getMenuItems();
            for (int i = 0; i< items.size(); i++){
                System.out.println((i + 1) + ". " + items.get(i));
            }
        } else {
            System.out.println("MainMenu is empty");
        }
    }

    public void navigate(Integer index){
        MenuItem item = currentMenu.getMenuItems().get(index);
        if (item != null && item.getAction() != null) {
            item.doAction();
        } else {
            item.getNextMenu();
        }
    }
}
