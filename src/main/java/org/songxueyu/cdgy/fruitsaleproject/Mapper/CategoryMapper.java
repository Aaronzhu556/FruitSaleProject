package org.songxueyu.cdgy.fruitsaleproject.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.songxueyu.cdgy.fruitsaleproject.DTO.CategoryDTO;

import java.util.List;

@Mapper
public interface CategoryMapper {
    public List<CategoryDTO> getAllCategory(String queryText);
    public List<CategoryDTO> getAllParentCategory(String category_level);
    public int addCategory(CategoryDTO category);
    public String queryNameById(String category_id);
    public String queryParentById(String category_id);
}
