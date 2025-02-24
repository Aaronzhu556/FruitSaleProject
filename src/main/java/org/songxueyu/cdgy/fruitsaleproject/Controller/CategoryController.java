package org.songxueyu.cdgy.fruitsaleproject.Controller;

import lombok.extern.slf4j.Slf4j;
import org.songxueyu.cdgy.fruitsaleproject.DTO.CategoryDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Category;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.songxueyu.cdgy.fruitsaleproject.Response.MyResponse;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.CategoryService;
import org.songxueyu.cdgy.fruitsaleproject.Util.BuildTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/category")
@Controller
@Slf4j
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/searchallcategory")
    @ResponseBody
    public MyResponse searchAllCategory(@RequestBody QueryInfo queryInfo, HttpServletResponse httpServletResponse) {
        List<CategoryDTO> allCategory = categoryService.getAllCategory(queryInfo);
        if (!allCategory.isEmpty())
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .info(String.valueOf(allCategory.size()))
                    .object(allCategory).build();
        else return MyResponse.builder()
                .code("201")
                .msg("没有查到数据")
                .object(null).build();
    }
    @RequestMapping("/getparent")
    @ResponseBody
    public MyResponse getParent(@RequestParam String category_level, HttpServletResponse httpServletResponse) {
        List<CategoryDTO> allParentCategory = categoryService.getAllParentCategory(category_level);
        List<CategoryDTO> categoryDTOS = BuildTreeUtil.BuildTreeForCategoryDTO(allParentCategory);
        return MyResponse.builder()
                .code("200")
                .msg("查询成功")
                .object(categoryDTOS).build();
    }
    @RequestMapping("/addcategory")
    @ResponseBody
    public MyResponse addCategory(@RequestBody CategoryDTO categoryDTO, HttpServletResponse httpServletResponse) {
        String s = categoryService.addCategory(categoryDTO);
        if (s.equals("200")){
            return MyResponse.builder()
                    .code(s)
                    .msg("添加分类成功")
                    .build();
        }
        else return MyResponse.builder()
                .code(s)
                .msg("添加分类失败").build();


    }


}
