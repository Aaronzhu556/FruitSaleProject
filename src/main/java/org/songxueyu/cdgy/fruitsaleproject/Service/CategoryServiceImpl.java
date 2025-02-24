package org.songxueyu.cdgy.fruitsaleproject.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.songxueyu.cdgy.fruitsaleproject.DTO.CategoryDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.CategoryMapper;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.CategoryService;
import org.songxueyu.cdgy.fruitsaleproject.Util.BuildTreeUtil;
import org.songxueyu.cdgy.fruitsaleproject.Util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<CategoryDTO> getAllCategory(QueryInfo queryInfo) {
        List<CategoryDTO> allCategory = categoryMapper.getAllCategory(queryInfo.getQuerytext());

        List<CategoryDTO> categoryList_new = new LinkedList<>();
        int count =0;
        if (!allCategory.isEmpty()) {
            List<CategoryDTO> categoryDTOS = BuildTreeUtil.BuildTreeForCategoryDTO(allCategory);
            for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize() && i < categoryDTOS.size(); i++) {
                categoryList_new.add(categoryDTOS.get(i));
                count++;
            }
        }
        log.info("后端数据：{}",categoryList_new);
        return categoryList_new;
    }

    @Override
    public List<CategoryDTO> getAllParentCategory(String category_level) {
        return categoryMapper.getAllParentCategory(category_level);
    }

    @Override
    public String addCategory(CategoryDTO category) {
        category.setCategory_dto_id(UuidUtil.getUuid());
        int i = categoryMapper.addCategory(category);
        if (i>0) return "200";
        else return "201";
    }

    @Override
    public String queryNameById(String category_id) {
        return categoryMapper.queryNameById(category_id);
    }
}
