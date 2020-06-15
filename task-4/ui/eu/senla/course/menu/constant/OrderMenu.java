package eu.senla.course.menu.constant;

public enum OrderMenu {
    ORDER("Order menu"),
    ADD("Add order"),
    IN_PROGRESS_STATUS("Change status an order to in progress"),
    CANCEL_STATUS("Change status an order to cancel"),
    CLOSE_STATUS("Change status an order to close"),
    DELETE_STATUS("Change status an order to delete"),
    CANCEL_BY_COMPLETE("Sort canceled orders by complete date"),
    CLOSE_BY_COMPLETE("Sort closed orders by complete date"),
    DELETE_BY_COMPLETE("Sort deleted orders by complete date"),
    IN_PROGRESS_BY_COMPLETE("Sort in progress orders by complete date"),
    CANCEL_BY_REQUEST("Sort canceled orders by request date"),
    CLOSE_BY_REQUEST("Sort closed orders by request date"),
    DELETE_BY_REQUEST("Sort deleted orders by request date"),
    IN_PROGRESS_BY_REQUEST("Sort in progress orders by request date"),
    CANCEL_BY_PLANNED("Sort canceled orders by planned date"),
    CLOSE_BY_PLANNED("Sort closed orders by planned date"),
    DELETE_BY_PLANNED("Sort deleted orders by planned date"),
    IN_PROGRESS_BY_PLANNED("Sort in progress orders by planned date"),
    CANCEL_BY_PRICE("Sort canceled orders by price"),
    CLOSE_BY_PRICE("Sort closed orders by price"),
    DELETE_BY_PRICE("Sort deleted orders by price"),
    IN_PROGRESS_BY_PRICE("Sort in progress orders by price"),
    CURRENT_BY_COMPLETE("Sort current orders by complete date"),
    CURRENT_BY_REQUEST("Sort current orders by request date"),
    CURRENT_BY_PLANNED("Sort current orders by planned date"),
    CURRENT_BY_PRICE("Sort current orders by price"),
    FIND_ORDER("Find order"),
    FIND_MECHANIC("Find mechanic"),
    NEXT_DATE("Next available date"),
    BILL("Bill for order"),
    DELETE("Delete order");

    private String name;

    OrderMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
