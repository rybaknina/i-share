package eu.senla.course.enums;

public enum SpotMenu {
    SPOT("Spot menu"),
    ADD("Add spot"),
    DELETE("Delete spot"),
    GET_ALL("List of spots"),
    IMPORT("Import spots from csv"),
    EXPORT("Export spots to csv");

    private String name;

    SpotMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
