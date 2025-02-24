package org.songxueyu.cdgy.fruitsaleproject.Service.Interface;

import org.songxueyu.cdgy.fruitsaleproject.Entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {
     int addOrder(Order order);
     List<Order> getAllOrders();
     int deliverOrder(String order_id, String order_user_email);
     List<Order> getOrdersByUser(String order_user_name);
     int receiveOrder(String order_id);
     List<Order> getOrderWithoutComment(String user_name);
     int addOrderComment(Integer order_star, String order_comment_content, String order_id);
     Map<String,Integer> getOrdersSevenDate();
}
