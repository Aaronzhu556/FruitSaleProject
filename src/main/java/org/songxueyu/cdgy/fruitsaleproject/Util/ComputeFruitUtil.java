package org.songxueyu.cdgy.fruitsaleproject.Util;

import lombok.extern.apachecommons.CommonsLog;
import org.songxueyu.cdgy.fruitsaleproject.DTO.FruitDTO;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
public class ComputeFruitStarUtil {
    @Autowired
    private OrderMapper orderMapper;
    public List<FruitDTO> computeFruitStar(List<FruitDTO> fruitDTOList) {
        for (FruitDTO fruitdto : fruitDTOList) {
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
        }
        return fruitDTOList;
    }
}
