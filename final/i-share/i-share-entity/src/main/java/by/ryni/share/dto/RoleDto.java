package by.ryni.share.dto;

import javax.validation.constraints.NotEmpty;

public class RoleDto extends AbstractDto {
    @NotEmpty
    private String name;

    public RoleDto() {
    }

    public RoleDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
