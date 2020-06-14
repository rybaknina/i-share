package eu.senla.course.menu.constant;

public enum OrderMenu {
    ORDER("Order menu"),
    ADD("Add order");

    private String name;

    OrderMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
