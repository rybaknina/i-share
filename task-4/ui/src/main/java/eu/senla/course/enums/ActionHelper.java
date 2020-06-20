package eu.senla.course.enums;

public enum ActionHelper {
    IN_INTEGER("Input an Integer: "),
    IN_STRING("Input a String: "),
    IN_BIG_DECIMAL("Input a Big Decimal: "),
    IN_LOCAL_DATE_TIME("Input a LocalDateTime in format \"dd.MM.yyyy HH:mm\" or \"yyyy-MM-dd HH:mm\": "),
    IN_LOCAL_DATE("Input a Local Date in format \"dd.MM.yyyy\" or \"yyyy-MM-dd\": ");

    private String name;

    ActionHelper(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
