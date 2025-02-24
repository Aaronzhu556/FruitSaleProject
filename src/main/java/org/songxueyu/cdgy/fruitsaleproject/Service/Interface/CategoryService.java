package org.songxueyu.cdgy.fruitsaleproject.Service.Interface;

import org.songxueyu.cdgy.fruitsaleproject.DTO.CategoryDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    public List<CategoryDTO> getAllCategory(QueryInfo queryInfo);
    public List<CategoryDTO> getAllParentCategory(String category_level);
    public String addCategory(CategoryDTO category);
    public String queryNameById(String category_id);
}
