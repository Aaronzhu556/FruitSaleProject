package org.songxueyu.cdgy.fruitsaleproject.Util;

import lombok.extern.slf4j.Slf4j;
import org.songxueyu.cdgy.fruitsaleproject.DTO.FruitCommentDTO;
import org.songxueyu.cdgy.fruitsaleproject.DTO.FruitDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Fruit;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Order;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.CategoryMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.OrderMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ComputeFruitUtil {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserMapper userMapper;
    public FruitDTO computeFruitStar(FruitDTO fruitdto) {

        List<Integer> allStarByFruitId = orderMapper.getAllStarByFruitId(fruitdto.getFruit_dto_id());
        if (!allStarByFruitId.isEmpty()){
            int temp=0;
            double star = 0,star_temp=0;
            for (int num:allStarByFruitId) temp=temp+num;
            if (temp>0) {
                star = (float) temp / allStarByFruitId.size();
                BigDecimal bigDecimal = new BigDecimal(star);
                star_temp = bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();
            }
            fruitdto.setFruit_dto_star(star_temp);
        }
        else{
            fruitdto.setFruit_dto_star(0.0);
        }

        return fruitdto;
    }

    public FruitDTO computeFruitComment(FruitDTO fruitDTO){
        List<Order> orders = orderMapper.getOrdersByFruitId(fruitDTO.getFruit_dto_id());

        List<FruitCommentDTO> fruitCommentDTOList = orders.stream()
                .map(order ->{
                    FruitCommentDTO fruitCommentDTO = new FruitCommentDTO();
                    fruitCommentDTO.setFruit_comment_id(UuidUtil.getUuid());
                    fruitCommentDTO.setFruit_comment_content(order.getOrder_comment_content());
                    fruitCommentDTO.setFruit_comment_user_img(userMapper.GetUserImgByName(order.getOrder_user_name()));
                    fruitCommentDTO.setFruit_comment_user_name(order.getOrder_user_name());
                    fruitCommentDTO.setFruit_comment_date(order.getOrder_comment_time());
                    fruitCommentDTO.setFruit_id(order.getOrder_fruit_id());
                    fruitCommentDTO.setFruit_comment_star(order.getOrder_star());
                    return fruitCommentDTO;
        }).collect(Collectors.toList());
        fruitDTO.setFruit_dto_comment_list(fruitCommentDTOList);
        return fruitDTO;
    }

    public FruitDTO computeFruitCategory(FruitDTO fruitDTO){
        List<String> NameList = new LinkedList<>();
        log.info("后端数据,{}",fruitDTO);
        String category_id = fruitDTO.getFruit_dto_category();
        String parent;
        do {
            NameList.add(categoryMapper.queryNameById(category_id));
            parent = categoryMapper.queryParentById(category_id);
            log.info("category_id:{},parent:{}"+category_id+parent);
            // log.info("parent is"+parent);
            category_id = parent;
        }while (!parent.equals("0"));
        fruitDTO.setFruit_dto_category(NameList.toString());
        return fruitDTO;
    }
    public FruitDTO computeFruitOrderNum(FruitDTO fruitDTO){
         try{
             fruitDTO.setFruit_dto_order_num(orderMapper.getOrderFruitNumByFruitId(fruitDTO.getFruit_dto_id()));
         }catch (Exception e){
             fruitDTO.setFruit_dto_order_num(0);
         }

         return fruitDTO;
    }
}
