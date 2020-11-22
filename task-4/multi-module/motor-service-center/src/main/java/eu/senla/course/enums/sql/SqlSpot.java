package eu.senla.course.enums.sql;

public enum SqlSpot {
    ID("id"),
    GARAGE_ID("garage_id"),
    INSERT("insert into spot (garage_id) values (?);"),
    DELETE("delete from spot where id = ?;"),
    SELECT_BY_ID("select garage_id from spot where id = ?;"),
    SELECT_ALL("select id, garage_id from spot;"),
    UPDATE("update spot set garage_id = ? where id = ?;"),
    DELETE_ALL("delete from spot;"),
    RESET("alter table spot auto_increment = 1;");

    private String name;

    SqlSpot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
