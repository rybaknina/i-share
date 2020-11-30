package by.ryni.share.dto;

import java.io.Serializable;

public abstract class AbstractDto implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
