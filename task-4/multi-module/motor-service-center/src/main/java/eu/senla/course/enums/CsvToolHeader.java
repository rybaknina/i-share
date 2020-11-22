package eu.senla.course.enums;

public enum CsvToolHeader {
    ID("id"),
    NAME("name"),
    HOURS("hours"),
    HOURLY_PRICE("hourly_price");

    private String name;

    CsvToolHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
