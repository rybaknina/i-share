package eu.senla.course.enums;

public enum SpotMenu {
    SPOT("Spot menu"),
    ADD("Add spot"),
    DELETE("Delete spot"),
    GET_ALL("List of spots");

    private String name;

    SpotMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
