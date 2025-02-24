package org.songxueyu.cdgy.fruitsaleproject.Entity;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class Comment {
    private String comment_id;
    private String comment_user_name;
    private String comment_time;
    private String comment_content;
    private String comment_article_id;
}
