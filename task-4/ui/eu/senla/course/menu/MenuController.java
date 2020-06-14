package eu.senla.course.menu;

import eu.senla.course.menu.constant.MainMenu;
import eu.senla.course.util.InputValidator;
import java.util.Scanner;

public class MenuController {

    private Builder builder = Builder.getInstance();
    private Navigator navigator = Navigator.getInstance();

    public void run(){
        navigator.setCurrentMenu(builder.getRootMenu());
        navigator.printMenu();

        boolean exit = false;

        try (Scanner in = new Scanner(System.in)) {

            while (!exit) {
                // TODO: Fix bug after add in menu - fall - can't fix yet
                int input = InputValidator.readInteger(in) - 1;

                if (input < 0 || input >= navigator.getCurrentMenu().getMenuItems().size()) {
                    System.out.println("Try again...");
                    continue;
                } else {
                    navigator.navigate(input);
                }

                if (navigator.getCurrentMenu().getMenuItems().get(input).getTitle().equals(MainMenu.EXIT.getName())) {
                    exit = true;
                    continue;
                }

                navigator.setCurrentMenu(navigator.getCurrentMenu().getMenuItems().get(input).getNextMenu());
                navigator.printMenu();
            }
        }
        System.out.println("I'll be back ... soon");
    }
}
