package org.songxueyu.cdgy.fruitsaleproject.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    int insertOrder(Order order);
    List<Integer> getAllStarByFruitId(String fruit_id);
    List<Order> getOrdersByFruitId(String fruit_id);
    Integer getOrderFruitNumByFruitId(String fruit_id);
    List<Order> getAllOrders();
    int updateOrderStatus(String order_id,String order_status);
    List<Order> getAllOrderByUser(String user_name);
    List<Order> getOrderWithoutComment(String user_name);
    int addOrderComment(Order order);
    int addOrderReceiveTime(String order_receive_time,String order_id);
    List<Order> getAllOrdersByFruitId(String fruit_id);
}
