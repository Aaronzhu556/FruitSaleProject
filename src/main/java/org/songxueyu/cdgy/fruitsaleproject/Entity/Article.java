package org.songxueyu.cdgy.fruitsaleproject.Entity;


import lombok.Data;

import java.util.List;

@Data
public class Article {
    private String article_id;
    private String article_title;
    private String article_content;
    private int article_reply_num;
    private int article_star;
    private String article_reply;
    private String article_user_name;
    private String article_time;
    private int article_click;



}
