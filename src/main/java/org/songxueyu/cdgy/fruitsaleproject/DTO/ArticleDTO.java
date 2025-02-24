package org.songxueyu.cdgy.fruitsaleproject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    @JsonProperty("article_id")
    private String article_dto_id;
    @JsonProperty("article_title")
    private String article_dto_title;
    @JsonProperty("article_content")
    private String article_dto_content;
    @JsonProperty("article_reply_num")
    private int article_dto_reply_num;
    @JsonProperty("article_star")
    private int article_dto_star;
    @JsonProperty("article_reply")
    private String article_dto_reply;
    @JsonProperty("article_user_name")
    private String article_dto_user_name;
    @JsonProperty("article_time")
    private String article_dto_time;
    @JsonProperty("article_click")
    private int article_dto_click;

    @JsonProperty("article_user_img")
    private String article_dto_user_img;
    @JsonProperty("article_reply_list")
    private List<Integer> article_dto_reply_list= new LinkedList<>();;
    @JsonProperty("article_user_like")
    private boolean article_dto_user_like;
    @JsonProperty("article_hot")
    private double article_dto_hot;

    public void setArticle_dto_reply_list(List<Integer> article_dto_reply_list) {
        this.article_dto_reply_list = article_dto_reply_list;
    }
}
