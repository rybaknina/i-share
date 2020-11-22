package eu.senla.course.enums.sql;

public enum SqlOrder {
    ID("id"),
    REQUEST_DATE("request_date"),
    PLANNED_DATE("planned_date"),
    START_DATE("start_date"),
    COMPLETE_DATE("complete_date"),
    PRICE("price"),
    STATUS("status"),
    MECHANIC_ID("mechanic_id"),
    SPOT_ID("spot_id"),
    INSERT("insert into `order` (request_date, planned_date, mechanic_id, spot_id) values (?, ?, ?, ?);"),
    DELETE("delete from `order` where id = ?;"),
    SELECT_BY_ID("select request_date, planned_date, start_date, complete_date, price, status , mechanic_id, spot_id from `order` where id = ?;"),
    SELECT_ALL("select id, request_date, planned_date, start_date, complete_date, price, status , mechanic_id, spot_id from `order`;"),
    UPDATE("update `order` set request_date = ?, planned_date = ?, start_date = ?, complete_date = ?, price = ?, status = ? , mechanic_id = ?, spot_id = ? where id = ?;"),
    DELETE_ALL("delete from `order`;"),
    RESET("alter table `order` auto_increment = 1;");

    private String name;

    SqlOrder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
