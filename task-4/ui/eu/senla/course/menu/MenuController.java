package eu.senla.course.menu;

import eu.senla.course.menu.constant.MainMenu;
import eu.senla.course.util.InputValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuController {

    private Builder builder = Builder.getInstance();
    private Navigator navigator = Navigator.getInstance();

    public void run() throws IOException {
        navigator.setCurrentMenu(builder.getRootMenu());
        navigator.printMenu();

        boolean exit = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        try (Scanner in = new Scanner(System.in)) {
  //      try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            while (!exit) {
                // TODO: Fix bug after add reader menu - fall - can't fix yet
                int input = InputValidator.readInteger(reader) - 1;

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
   //     }
        reader.close();
        System.out.println("I'll be back ... soon");
    }
}
