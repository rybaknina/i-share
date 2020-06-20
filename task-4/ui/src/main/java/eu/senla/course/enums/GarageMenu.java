package eu.senla.course.enums;

public enum GarageMenu {
    GARAGE("Garage menu"),
    ADD("Add garage"),
    DELETE("Delete garage"),
    GET_ALL("Get list of garages"),
    LIST_FREE_SPOTS("List Available Spots on future date"),
    NUMBER_FREE_SPOTS("Number Available Spots on future date"),
    IMPORT("Import Garages from csv");

    private String name;

    GarageMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
