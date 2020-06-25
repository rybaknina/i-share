package eu.senla.course.enums;

public enum CsvMechanicHeader {
    ID("id"),
    NAME("name"),
    ORDER_IDS("order_ids"),
    GARAGE_ID("garage_id");

    private String name;

    CsvMechanicHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
