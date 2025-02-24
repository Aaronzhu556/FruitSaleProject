package com.g12.cw1.Utils.Interface;

import org.springframework.stereotype.Component;

@Component
public interface EmailSender {
    /**
     * 发送文本邮件
     * */
    public void sendSimpleEmail(String to, String subject, String content);
    public void sendHtmlEmail(String to ,String subject,String content);

}
