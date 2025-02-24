package org.songxueyu.cdgy.fruitsaleproject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable {
    @JsonProperty("category_id")
    private String category_dto_id;
    @JsonProperty("category_name")
    private String category_dto_name;
    @JsonProperty("category_parent")
    private String category_dto_parent;
    @JsonProperty("category_level")
    private String category_dto_level;
    @JsonProperty("category_status")
    private String category_dto_status;
    @JsonProperty("category_children")
    private List<CategoryDTO> category_dto_children = new LinkedList<>();

    public void setCategory_dto_children(List<CategoryDTO> category_dto_children) {
        this.category_dto_children.addAll(category_dto_children);
    }

}
