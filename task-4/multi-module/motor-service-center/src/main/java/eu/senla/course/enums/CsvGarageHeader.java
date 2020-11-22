package eu.senla.course.enums;

public enum CsvGarageHeader {
    ID("id"),
    NAME("name"),
    SPOT_IDS("spot_ids"),
    MECHANIC_IDS("mechanic_ids");

    private String name;

    CsvGarageHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
