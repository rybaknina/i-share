package eu.senla.course.enums;

public enum MainMenu {
    SUB("Sub-menu"),
    ROOT("Home menu"),
    EXIT("Quit"),
    RETURN("Back to Main menu");

    private String name;

    MainMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
