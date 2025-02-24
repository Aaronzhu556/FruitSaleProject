package org.songxueyu.cdgy.fruitsaleproject.Aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.songxueyu.cdgy.fruitsaleproject.Util.Interface.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopAdviceEmailSender {
    @Autowired
    private EmailSender emailSender;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut(value = "execution(* org.songxueyu.cdgy.fruitsaleproject.Service.OrderServiceImpl.*(..))")
    public void aop(){}
    @After(value = "execution(* org.songxueyu.cdgy.fruitsaleproject.Service.OrderServiceImpl.deliverOrder(String,String))&&args(order_id,order_user_email)")
    public void afterDeliverOrder(String order_id,String order_user_email){
        try {
            emailSender.sendHtmlEmail(order_user_email,"发货提醒","您的订单:"+order_id+"已经发货啦!");
        }catch (Exception e){
            logger.info("{}",e);
        }
    }
}
