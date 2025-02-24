package org.songxueyu.cdgy.fruitsaleproject.Mapper;


import org.apache.ibatis.annotations.Mapper;

import org.songxueyu.cdgy.fruitsaleproject.DTO.CommentDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {

    public List<CommentDTO> QueryAllCommentByArticleId(String comment_article_id);

    public int InsertComment(CommentDTO comment);


    public int deleteCommentByArticle(String article_id);


    public int deleteCommentById(String comment_id);


    public String GetCommentArticle_id(String comment_id);
}
