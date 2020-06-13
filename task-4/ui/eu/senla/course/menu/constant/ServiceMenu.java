package eu.senla.course.menu.constant;

public enum ServiceMenu {
    SERVICE("Service menu"),
    ADD("Add service");

    private String name;

    ServiceMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
