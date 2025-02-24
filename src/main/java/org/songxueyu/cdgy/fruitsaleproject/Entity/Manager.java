package org.songxueyu.cdgy.fruitsaleproject.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Manager {
    private String manager_id;
    private String manager_name;
    private String manager_password;
}
