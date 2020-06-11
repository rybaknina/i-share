package eu.senla.course.menu;

import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MenuController {
    private Builder builder;
    private Navigator navigator;

    public MenuController() {
        this.builder = new Builder();
        this.navigator = new Navigator();
    }

    public void run(){

        navigator.setCurrentMenu(builder.buildMenu());
        navigator.printMenu();
        boolean exit = false;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            while (!exit) {

              //  LocalDateTime dateTime = InputValidator.readDateTime(reader); - just for test
              //  BigDecimal decimal = InputValidator.readDecimal(reader);

                int input = InputValidator.readInteger(reader) - 1;

                if (navigator.getCurrentMenu() == null){
                    exit = true;
                    continue;
                }
                List<MenuItem> items = navigator.getCurrentMenu().getMenuItems();
                if (items == null || input < 0 || items.size() <= input){
                    System.out.println("You made the wrong choice. Try again...");
                    continue;
                } else {
                    navigator.navigate(input);
                }
                if (items.get(input).getNextMenu() == null){
                    exit = true;
                    continue;
                }
                navigator.setCurrentMenu(items.get(input).getNextMenu());
                navigator.printMenu();
            }

            System.out.println("I'll be back ... soon");

        } catch (IOException e) {
            System.out.println("Something wrong happens...");
        }
    }
}
