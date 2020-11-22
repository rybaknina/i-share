package by.ryni.share.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public abstract class AbstractShortDto implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
