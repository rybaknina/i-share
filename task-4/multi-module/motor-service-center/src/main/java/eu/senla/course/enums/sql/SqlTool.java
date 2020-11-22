package eu.senla.course.enums.sql;

public enum SqlTool {
    ID("id"),
    NAME("name"),
    HOURS("hours"),
    HOURLY_PRICE("hourly_price"),
    ORDER_ID("order_id"),
    INSERT("insert into tool (name, hours, hourly_price, order_id) values (?, ?, ?, ?);"),
    DELETE("delete from tool where id = ?;"),
    SELECT_BY_ID("select name, hours, hourly_price, order_id from tool where id = ?;"),
    SELECT_ALL("select id, name, hours, hourly_price, order_id from tool;"),
    UPDATE("update tool set name = ?, hours = ?, hourly_price = ?, order_id = ? where id = ?;"),
    DELETE_ALL("delete from tool;"),
    RESET("alter table tool auto_increment = 1;");

    private String name;

    SqlTool(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
