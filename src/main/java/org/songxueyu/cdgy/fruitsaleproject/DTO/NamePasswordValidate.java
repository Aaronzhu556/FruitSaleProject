package org.songxueyu.cdgy.fruitsaleproject.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NamePasswordValidate {
    private String name;
    private String password;
    private String role;
}
