package org.songxueyu.cdgy.fruitsaleproject.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Fruit {
    private String fruit_id;
    private String fruit_name;
    private String fruit_price; //单价，总价= price*购买的份数
    private String fruit_category;
    private Integer fruit_stock; //库存
    private String fruit_unit; //单位，比如一份是多少kg
    private String fruit_description;
    private String fruit_image;
    private String fruit_address; //水果产地
    private String fruit_status; //水果状态 1-正常 0-缺货


}
