package eu.senla.course.enums.sql;

public enum SqlGarage {
    ID("id"),
    NAME("name"),
    INSERT("insert into garage (name) values (?);"),
    DELETE_SPOTS_IN_GARAGE("delete from spot where garage_id = ?;"),
    DELETE("delete from garage where id = ?;"),
    SELECT_BY_ID("select name from garage where id = ?;"),
    SELECT_ALL("select id, name from garage;"),
    UPDATE("update garage set name = ? where id = ?;");

    private String name;

    SqlGarage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
