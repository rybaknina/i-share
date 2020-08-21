package eu.senla.course.enums.sql;

public enum SqlMechanic {
    ID("id"),
    NAME("name"),
    GARAGE_ID("garage_id"),
    INSERT("insert into mechanic (name, garage_id) values (?, ?);"),
    DELETE("delete from mechanic where id = ?;"),
    SELECT_BY_ID("select name, garage_id from mechanic where id = ?;"),
    SELECT_ALL("select id, name, garage_id from mechanic;"),
    UPDATE("update mechanic set name = ?, garage_id = ? where id = ?;"),
    DELETE_ALL("delete from mechanic;"),
    RESET("alter table mechanic auto_increment = 1;");;

    private String name;

    SqlMechanic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
