package org.songxueyu.cdgy.fruitsaleproject.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.songxueyu.cdgy.fruitsaleproject.DTO.FruitDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Order;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.FruitMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.OrderMapper;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.OrderService;
import org.songxueyu.cdgy.fruitsaleproject.Util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private FruitMapper fruitMapper;
    @Override
    public synchronized int  addOrder(Order order) {
        order.setOrder_id(UuidUtil.getUuid());
        order.setOrder_price(String.valueOf(Integer.parseInt(order.getOrder_fruit_price())*order.getOrder_fruit_num()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        order.setOrder_create_time(sdf.format(date));
        int stock = fruitMapper.getFruitStockById(order.getOrder_fruit_id());
        int stock_new = stock - order.getOrder_fruit_num();
        fruitMapper.updateFruitStock(order.getOrder_fruit_id(),stock_new);
        return orderMapper.insertOrder(order);

    }

    @Override
    public List<Order> getAllOrders() {

        return orderMapper.getAllOrders();
    }

    @Override
    public int deliverOrder(String order_id, String order_user_email) {
        return orderMapper.updateOrderStatus(order_id,"2");
    }

    @Override
    public List<Order> getOrdersByUser(String order_user_name) {
        List<Order> allOrderByUser = orderMapper.getAllOrderByUser(order_user_name);
        log.info("{}",allOrderByUser);
        /*
        Iterator<Order> iterator = allOrderByUser.iterator();
        while (iterator.hasNext()){
            Order order = iterator.next();
            FruitDTO fruitById = fruitMapper.getFruitById(order.getOrder_fruit_id());
            if (fruitById==null) iterator.remove();
            else order.setOrder_fruit_name(fruitById.getFruit_dto_name());
        }
        */

        return  allOrderByUser;
    }

    @Override
    public int receiveOrder(String order_id) {
        int flag = 1;
        try {
            orderMapper.updateOrderStatus(order_id,"3");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            orderMapper.addOrderReceiveTime(sdf.format(date).toString(),order_id);
        }catch (Exception e){
            flag = 0;
            log.info("{}",e);
        }
        return flag;
    }

    @Override
    public List<Order> getOrderWithoutComment(String user_name) {
        List<Order> orderWithoutComment = orderMapper.getOrderWithoutComment(user_name);
        for(Order order:orderWithoutComment){
            FruitDTO fruitById = fruitMapper.getFruitById(order.getOrder_fruit_id());
            order.setOrder_fruit_name(fruitById.getFruit_dto_name());
        }
        return orderWithoutComment;
    }

    @Override
    public int addOrderComment(Integer order_star, String order_comment_content, String order_id) {
        int flag=1;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            orderMapper.addOrderComment(Order.builder()
                    .order_id(order_id)
                    .order_star(order_star)
                    .order_comment_content(order_comment_content)
                    .order_comment_time(sdf.format(date).toString()).build());
        }catch (Exception e){
            flag = 0;
            log.info("{}",e);
        }

        return flag;
    }

    @Override
    public Map<String ,Integer> getOrdersSevenDate() {
        List<Order> allOrders = orderMapper.getAllOrders();
        // 使用 TreeMap 存储日期和销量的映射，自动按日期排序
        Map<String, Integer> salesMap = new TreeMap<>();
        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 遍历订单列表
        for (Order order : allOrders) {
            // 解析订单日期，先解析为 LocalDateTime，再转换为 LocalDate
            LocalDate orderDate = LocalDateTime.parse(order.getOrder_create_time(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
            if (orderDate.isAfter(oneWeekAgo) && orderDate.isBefore(today) || orderDate.isEqual(oneWeekAgo) || orderDate.isEqual(today)) {
                String dateStr = orderDate.format(formatter);
                int quantity = order.getOrder_fruit_num();
                salesMap.put(dateStr, salesMap.getOrDefault(dateStr, 0) + quantity);
            }
        }

        // 补充没有订单的日期，销量为 0
        for (int i = 0; i < 7; i++) {
            LocalDate date = today.minusDays(i);
            String dateStr = date.format(formatter);
            salesMap.putIfAbsent(dateStr, 0);
        }
        return salesMap;
    }
}
