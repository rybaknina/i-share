package eu.senla.course.menu;

import eu.senla.course.action.load.LoadFromFileAction;
import eu.senla.course.action.load.LoadToFileAction;
import eu.senla.course.enums.MainMenu;
import eu.senla.course.util.AppConfig;
import eu.senla.course.util.ConnectionUtil;
import eu.senla.course.util.InputValidator;
import eu.senla.course.util.JPAUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

final public class MenuController {
    private final static MenuController instance = new MenuController();
    private Builder builder = Builder.getInstance();
    private Navigator navigator = Navigator.getInstance();
    private final static Logger logger = LogManager.getLogger(MenuController.class);
    private MenuController() {

        new AppConfig();

        try {
            new LoadFromFileAction().execute();
        } catch (IOException e) {
            logger.warn("File is broken " + e.getMessage());
        }
    }

    public static MenuController getInstance() {
        return instance;
    }

    public void run() throws IOException {

        builder.buildMenu();
        navigator.setCurrentMenu(builder.getRootMenu());
        navigator.printMenu();

        boolean exit = false;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (!exit) {

                int input = InputValidator.readInteger(reader) - 1;

                if (input < 0 || input >= navigator.getCurrentMenu().getMenuItems().size()) {
                    System.out.println("Try again...");
                    continue;
                } else {
                    navigator.navigate(input);
                }

                if (navigator.getCurrentMenu().getMenuItems().get(input).getTitle().equals(MainMenu.EXIT.getName())) {
                    new LoadToFileAction().execute();
                    ConnectionUtil.getInstance().closeConnection();
                    JPAUtility.close();
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
