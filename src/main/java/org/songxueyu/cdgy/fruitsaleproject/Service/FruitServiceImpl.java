package org.songxueyu.cdgy.fruitsaleproject.Service;

import lombok.extern.slf4j.Slf4j;
import org.songxueyu.cdgy.fruitsaleproject.DTO.FruitDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Fruit;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Order;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.CategoryMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.FruitMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.OrderMapper;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.FruitService;
import org.songxueyu.cdgy.fruitsaleproject.Util.ComputeFruitUtil;
import org.songxueyu.cdgy.fruitsaleproject.Util.ImageUtil;
import org.songxueyu.cdgy.fruitsaleproject.Util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class FruitServiceImpl implements FruitService {
    @Autowired
    private FruitMapper fruitMapper;
    @Value("${fruit.image.path}")
    private String imagePath;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ComputeFruitUtil computeFruitUtil;
    @Override
    public List<FruitDTO> getAllFruits(QueryInfo queryInfo) {
        if (queryInfo.getQuerytext().equals("")){
            List<FruitDTO> allFruits = fruitMapper.getAllFruits();
            log.info("{}:",allFruits);
            for (FruitDTO fruitDTO:allFruits){
                computeFruitUtil.computeFruitStar(fruitDTO);
            }
            return  allFruits;
        }else if(queryInfo.getQuerytext().equals("根据类型排序")&& queryInfo.getQuerycateid()!=""){
            List<FruitDTO> allFruitsByCate = fruitMapper.getAllFruitsByCate(queryInfo.getQuerycateid());
            for (FruitDTO fruitDTO:allFruitsByCate){
                computeFruitUtil.computeFruitStar(fruitDTO);
            }
            return allFruitsByCate;
        }else{
            List<FruitDTO> allFruitsByName = fruitMapper.getAllFruitsByName(queryInfo.getQuerytext());
            //log.info(queryInfo.getQuerytext()+"========================");
            for (FruitDTO fruitDTO:allFruitsByName){
                computeFruitUtil.computeFruitStar(fruitDTO);
            }
            return allFruitsByName;
        }
    }

    @Override
    public String uploadFruitImage(MultipartFile multipartFile) {
        return ImageUtil.uploadImg(multipartFile, imagePath, "");

    }
    @Override
    public int addFruit(Fruit fruit) {
        fruit.setFruit_id(UuidUtil.getUuid());
        if(fruitMapper.addFruit(fruit)!=0) return 1;
        else  return 0;



    }

    @Override
    public FruitDTO getFruitById(String fruit_id) {
        FruitDTO fruit = fruitMapper.getFruitById(fruit_id);

        fruit = computeFruitUtil.computeFruitCategory(fruit);
        fruit = computeFruitUtil.computeFruitComment(fruit);
        fruit = computeFruitUtil.computeFruitStar(fruit);
        log.info("fruit数据,{}",fruit);
        return fruit;
    }

    @Override
    public List<FruitDTO> getAllFruitsByOrderNum() {
        List<FruitDTO> allFruits = fruitMapper.getAllFruits();
        for (FruitDTO fruitDTO:allFruits){
            computeFruitUtil.computeFruitOrderNum(fruitDTO);
            computeFruitUtil.computeFruitStar(fruitDTO);
        }
        allFruits.sort(Comparator.comparing(FruitDTO :: getFruit_dto_order_num).reversed());
        return allFruits;
    }

    @Override
    public List<FruitDTO> getAllFruitsByStar() {
        List<FruitDTO> allFruits = fruitMapper.getAllFruits();
        for (FruitDTO fruitDTO:allFruits){
            computeFruitUtil.computeFruitStar(fruitDTO);
        }
        allFruits.sort(Comparator.comparing(FruitDTO::getFruit_dto_star).reversed());
        return allFruits;
    }

    @Override
    public List<FruitDTO> getAllFruitsByTime() {
        List<FruitDTO> allFruits = fruitMapper.getAllFruits();
        allFruits.sort(Comparator.comparing(FruitDTO::getFruit_dto_id).reversed());
        return allFruits;
    }

    @Override
    public int goOffFruit(String fruit_id,String fruit_status) {

        int flag = 0;
        List<Order> ordersByFruitId = orderMapper.getAllOrdersByFruitId(fruit_id);
        for(Order order:ordersByFruitId){
            if (!order.getOrder_status().equals("4")){
                flag = 1;
                break;
            }
        }
        if (flag == 1) return 2;
        else return fruitMapper.updateFruitStatus(fruit_id,fruit_status);
    }

    @Override
    public int deleteFruit(String fruit_id) {

        //查看是否正在进行中的订单

        return 0;
    }
}
