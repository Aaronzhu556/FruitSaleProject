package org.songxueyu.cdgy.fruitsaleproject.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.songxueyu.cdgy.fruitsaleproject.DTO.FruitDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Fruit;

import javax.swing.plaf.FontUIResource;
import java.util.List;

@Mapper
public interface FruitMapper {
    List<FruitDTO> getAllFruits();
    List<FruitDTO> getAllFruitsByCate(String fruit_category);
    List<FruitDTO> getAllFruitsByName(String fruit_name);
    int addFruit(Fruit fruit);
    FruitDTO getFruitById(String fruit_id);
    int updateFruitStock(String fruit_id, Integer fruit_stock);
    int getFruitStockById(String fruit_id);
    int updateFruitStatus(String fruit_id,String fruit_status);

}
