package org.songxueyu.cdgy.fruitsaleproject.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private String order_id;
    private String order_fruit_id;
    private String order_price;
    private String order_status;  //默认为1   1--创建订单未发货  2--已发货   3--已签收（已完成）未评论 4--已评论 0--已取消
    private String order_create_time;
    private String order_comment_content;  //默认为 null
    private String order_comment_time; //评论时间
    private String order_user_name;
    private String order_user_email;
    private String order_address;
    private Integer order_star; //默认为0
    private Integer order_fruit_num; //购买的水果数量-- int类型
    private String order_fruit_price;  //水果单价  -字符类型
    private String order_receive_time; //签收时间

    private String order_fruit_name;//订单水果名
}
