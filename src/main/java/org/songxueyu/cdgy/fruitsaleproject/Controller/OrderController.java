package org.songxueyu.cdgy.fruitsaleproject.Controller;

import lombok.extern.slf4j.Slf4j;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Fruit;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Order;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.songxueyu.cdgy.fruitsaleproject.Response.MyResponse;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequestMapping("/order")
@Controller
@Slf4j
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/orderfruit")
    @ResponseBody
    public MyResponse oderFruit(@RequestBody Order order, HttpServletResponse httpServletResponse){
        log.info("order is {}",order);
        int i = orderService.addOrder(order);
        if (i!=0) return MyResponse.builder()
                .code("200")
                .msg("创建订单成功")
                .build();
        else return MyResponse.builder()
                .info("201")
                .msg("创建订单失败")
                .build();
    }
    @RequestMapping("/getallorders")
    @ResponseBody
    public MyResponse getAllOrders(@RequestBody QueryInfo queryInfo){
        List<Order> allOrders = orderService.getAllOrders();
        if (!allOrders.isEmpty())
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .object(allOrders)
                    .info(String.valueOf(allOrders.size())).build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("查询失败")
                    .build();
    }
    @RequestMapping("/deliverorder")
    @ResponseBody
    public MyResponse deliverOrder(@RequestParam String order_id,@RequestParam String order_user_email, HttpServletResponse httpServletResponse){
        int i = orderService.deliverOrder(order_id, order_user_email);
        if (i!=0) return MyResponse.builder()
                .code("200")
                .msg("发货成功")
                .build();
        else return MyResponse.builder()
                .code("201")
                .msg("发货失败").build();
    }
    @RequestMapping("/getallorderbyuser")
    @ResponseBody
    public MyResponse getAllOrdersByUser(@RequestParam String user_name){
        List<Order> ordersByUser = orderService.getOrdersByUser(user_name);
        if (!ordersByUser.isEmpty())
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .object(ordersByUser).build();
        else return MyResponse.builder()
                .code("201")
                .msg("暂无订单").build();

    }
    @RequestMapping("/receiveorder")
    @ResponseBody
    public MyResponse receiveOrder(@RequestParam String order_id) {
        int i = orderService.receiveOrder(order_id);
        if (i!=0)
            return MyResponse.builder()
                    .code("200")
                    .msg("签收成功")
                    .build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("系统错误")
                    .build();
    }
    @RequestMapping("/getorderbyuserwithoutcomment")
    @ResponseBody
    public MyResponse getOrderWithoutComment(@RequestParam String user_name){
        List<Order> orderWithoutComment = orderService.getOrderWithoutComment(user_name);
        log.info("{}",orderWithoutComment);
        if (!orderWithoutComment.isEmpty())
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .object(orderWithoutComment).build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("查询失败")
                    .build();
    }
    @RequestMapping("/addcomment")
    @ResponseBody
    public MyResponse addComment(@RequestParam Integer order_star,@RequestParam String order_comment_content,@RequestParam String order_id){
        int i = orderService.addOrderComment(order_star, order_comment_content, order_id);
        if (i!=0)
            return MyResponse.builder()
                    .code("200")
                    .msg("评论成功")
                    .build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("系统错误").build();

    }
    @RequestMapping("/getordersevendate")
    @ResponseBody
    public MyResponse getOrderSevenDate(){
        Map<String, Integer> ordersSevenDate = orderService.getOrdersSevenDate();
        return MyResponse.builder()
                .code("200")
                .msg("查询成功")
                .object(ordersSevenDate).build();

    }

}
