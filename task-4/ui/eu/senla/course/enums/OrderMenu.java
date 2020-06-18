package eu.senla.course.enums;

public enum OrderMenu {
    ORDER("Order menu"),
    ADD("Add order"),
    DELETE("Delete order"),
    ADD_TOOLS("Add tools to order"),
    IN_PROGRESS_STATUS("Change status an order to in progress"),
    CANCEL_STATUS("Change status an order to cancel"),
    CLOSE_STATUS("Change status an order to close"),
    DELETE_STATUS("Change status an order to delete"),
    CANCEL_BY_COMPLETE("Sort canceled orders by complete date for period"),
    CLOSE_BY_COMPLETE("Sort closed orders by complete date for period"),
    DELETE_BY_COMPLETE("Sort deleted orders by complete date for period"),
    IN_PROGRESS_BY_COMPLETE("Sort in progress orders by complete date for period"),
    CANCEL_BY_REQUEST("Sort canceled orders by request date for period"),
    CLOSE_BY_REQUEST("Sort closed orders by request date for period"),
    DELETE_BY_REQUEST("Sort deleted orders by request date for period"),
    IN_PROGRESS_BY_REQUEST("Sort in progress orders by request date for period"),
    CANCEL_BY_PLANNED("Sort canceled orders by planned date for period"),
    CLOSE_BY_PLANNED("Sort closed orders by planned date for period"),
    DELETE_BY_PLANNED("Sort deleted orders by planned date for period"),
    IN_PROGRESS_BY_PLANNED("Sort in progress orders by planned date for period"),
    CANCEL_BY_PRICE("Sort canceled orders by price for period"),
    CLOSE_BY_PRICE("Sort closed orders by price for period"),
    DELETE_BY_PRICE("Sort deleted orders by price for period"),
    IN_PROGRESS_BY_PRICE("Sort in progress orders by price for period"),
    CURRENT_BY_COMPLETE("Sort current orders by complete date"),
    CURRENT_BY_REQUEST("Sort current orders by request date"),
    CURRENT_BY_PLANNED("Sort current orders by planned date"),
    CURRENT_BY_PRICE("Sort current orders by price"),
    FIND_ORDER("Find order"),
    FIND_MECHANIC("Find mechanic"),
    NEXT_DATE("Next available date"),
    BILL("Bill for order");

    private String name;

    OrderMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
