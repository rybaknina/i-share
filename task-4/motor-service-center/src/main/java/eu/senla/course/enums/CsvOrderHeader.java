package eu.senla.course.enums;

public enum CsvOrderHeader {
    ID("id"),
    REQUEST_DATE("requestDate"),
    PLANNED_DATE("plannedDate"),
    MECHANIC_ID("mechanic_id"),
    SPOT_ID("spot_id"),
    STATUS("status");

    private String name;

    CsvOrderHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
