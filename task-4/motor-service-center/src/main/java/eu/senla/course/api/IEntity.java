package eu.senla.course.api;

import java.io.Serializable;

public interface IEntity extends Serializable {
    int getId();
    void setId(int id);
}
