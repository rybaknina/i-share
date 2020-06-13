package eu.senla.course.menu.constant;

public enum GarageMenu {
    GARAGE("Garage menu"),
    ADD("Add garage");

    private String name;

    GarageMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
