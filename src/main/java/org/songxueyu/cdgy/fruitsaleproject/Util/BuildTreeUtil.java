package org.songxueyu.cdgy.fruitsaleproject.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.songxueyu.cdgy.fruitsaleproject.DTO.CategoryDTO;


import java.util.LinkedList;
import java.util.List;

/**
 * created by @Aaronzhu.Soton.ac.uk in 2023.11.11
 *
 * */
public class BuildTreeUtil {

    private static final Logger log = LoggerFactory.getLogger(BuildTreeUtil.class);

    public final static  List<CategoryDTO>  BuildTreeForCategoryDTO(List<CategoryDTO> categories ) {
        List<CategoryDTO> categoryList = GetRoot(categories);
        for (CategoryDTO categorydto:categoryList){
            categorydto.setCategory_dto_children(BuildChildren(categories,categorydto.getCategory_dto_id()));
        }
        return categoryList;
    }
    /*
    * @Params @categories:完整的categories @category_id:父类id
    * @Description: 把categories中的类型通过递归的方式构建树状类型
    * */
    public final static List<CategoryDTO> BuildChildren(List<CategoryDTO> categories,String category_id){
        List<CategoryDTO> categoryList = new LinkedList<>();
        for (CategoryDTO categorydto:categories){
            if (categorydto.getCategory_dto_parent().equals(category_id)) {
                categoryList.add(categorydto);
            }
        }
        for (CategoryDTO categorydto:categoryList){
            categorydto.setCategory_dto_children(BuildChildren(categories,categorydto.getCategory_dto_id()));;
        }
        if (categoryList.isEmpty()){
            return new LinkedList<>();
        }
        return categoryList;
    }
    /**
     *
     * @return :返回root
     * */
    public final static List<CategoryDTO> GetRoot(List<CategoryDTO> categories  ){
        List<CategoryDTO> categoryList = new LinkedList<>();
        log.info("{}",categories);
        for (CategoryDTO categorydto : categories){
            if (categorydto.getCategory_dto_parent().equals("0")) categoryList.add(categorydto);
        }
        return categoryList;
    }
}
