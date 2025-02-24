package org.songxueyu.cdgy.fruitsaleproject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FruitDTO implements Serializable {
    @JsonProperty("fruit_id")
    private String fruit_dto_id;
    @JsonProperty("fruit_name")
    private String fruit_dto_name;
    @JsonProperty("fruit_price")
    private String fruit_dto_price; //单价，总价= price*购买的份数
    @JsonProperty("fruit_category")
    private String fruit_dto_category;
    @JsonProperty("fruit_stock")
    private Integer fruit_dto_stock; //库存
    @JsonProperty("fruit_unit")
    private String fruit_dto_unit; //单位，比如一份是多少kg
    @JsonProperty("fruit_description")
    private String fruit_dto_description;
    @JsonProperty("fruit_image")
    private String fruit_dto_image;
    @JsonProperty("fruit_address")
    private String fruit_dto_address; //水果产地
    @JsonProperty("fruit_status")
    private String fruit_dto_status; //水果状态 1-正常 0-缺货
    @JsonProperty("fruit_star")
    private Double fruit_dto_star;
    @JsonProperty("fruit_comment_list")
    private List<FruitCommentDTO> fruit_dto_comment_list = new LinkedList<>();
    @JsonProperty("fruit_order_num")
    private int fruit_dto_order_num;

    public void setFruit_dto_comment_list(List<FruitCommentDTO> fruit_dto_comment_list) {
        this.fruit_dto_comment_list = fruit_dto_comment_list;
    }
}
