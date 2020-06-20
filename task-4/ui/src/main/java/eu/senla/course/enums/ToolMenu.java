package eu.senla.course.enums;

public enum ToolMenu {
    TOOL("Tool menu"),
    ADD("Add tool"),
    DELETE("Delete tool"),
    GET_ALL("List of tools");

    private String name;

    ToolMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
