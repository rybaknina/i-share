package by.ryni.share.dto.role;

import by.ryni.share.dto.base.AbstractShortDto;

public class RoleShortDto extends AbstractShortDto {
    private String name;

    public RoleShortDto() {
    }

    public RoleShortDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
