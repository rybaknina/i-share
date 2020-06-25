package eu.senla.course.enums;

public enum CsvSpotHeader {
    ID("id"),
    GARAGE_ID("garage_id");

    private String name;

    CsvSpotHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
