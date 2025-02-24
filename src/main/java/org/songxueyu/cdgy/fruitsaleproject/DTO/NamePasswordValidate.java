package org.songxueyu.cdgy.fruitsaleproject.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NamePasswordValidate {
    private String name;
    private String password;
    private String role;

    public NamePasswordValidate() {
    }

    public NamePasswordValidate(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
