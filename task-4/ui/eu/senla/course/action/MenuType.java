package eu.senla.course.action;

public enum MenuType {

    SUB_MENU("Sub menu"),
    ROOT_MENU("Main menu");
    private String name;

    MenuType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
