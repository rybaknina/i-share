package eu.senla.course.menu.constant;

public enum MechanicMenu {
    MECHANIC("Mechanic menu"),
    ADD("Add mechanic");

    private String name;

    MechanicMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
