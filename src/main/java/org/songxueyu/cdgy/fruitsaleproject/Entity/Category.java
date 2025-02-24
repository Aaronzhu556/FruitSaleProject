package org.songxueyu.cdgy.fruitsaleproject.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {
    private String category_id;
    private String category_name;
    private String category_parent;
    private String category_level;
    private String category_status;

}
