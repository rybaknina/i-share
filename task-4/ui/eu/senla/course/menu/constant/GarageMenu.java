package eu.senla.course.menu.constant;

public enum GarageMenu {
    GARAGE("Garage menu"),
    ADD("Add garage"),
    DELETE("Delete garage"),
    GET_ALL("Get list of garages"),
    LIST_FREE_SPOTS("List Available Spots"),
    NUMBER_FREE_SPOTS("Number Available Spots");

    private String name;

    GarageMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
