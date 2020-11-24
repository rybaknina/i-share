package by.ryni.share.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public abstract class AbstractDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
