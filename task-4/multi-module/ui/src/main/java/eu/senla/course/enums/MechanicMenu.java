package eu.senla.course.enums;

public enum MechanicMenu {
    MECHANIC("Mechanic menu"),
    ADD("Add mechanic"),
    DELETE("Delete mechanic"),
    GET_ALL("List of Mechanics"),
    SORT_BY_ALPHABET("Sort mechanics by alphabet"),
    SORT_BY_BUSY("Sort mechanics by busy"),
    IMPORT("Import mechanics from csv"),
    EXPORT("Export mechanics to csv");


    private String name;

    MechanicMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
