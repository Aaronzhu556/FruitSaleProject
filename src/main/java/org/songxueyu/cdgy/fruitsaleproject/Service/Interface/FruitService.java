package org.songxueyu.cdgy.fruitsaleproject.Service.Interface;

import org.songxueyu.cdgy.fruitsaleproject.DTO.FruitDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Fruit;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FruitService {
    List<FruitDTO> getAllFruits(QueryInfo queryInfo);
    String uploadFruitImage(MultipartFile multipartFile);
    int addFruit(Fruit fruit);
    FruitDTO getFruitById(String fruit_id);
    List<FruitDTO> getAllFruitsByOrderNum( );
    List<FruitDTO> getAllFruitsByStar();
    List<FruitDTO> getAllFruitsByTime();
    int goOffFruit(String fruit_id,String fruit_status);
    int deleteFruit(String fruit_id);
}
