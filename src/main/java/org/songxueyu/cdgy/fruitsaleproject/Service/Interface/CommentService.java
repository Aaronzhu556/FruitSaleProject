package org.songxueyu.cdgy.fruitsaleproject.Service.Interface;

import org.songxueyu.cdgy.fruitsaleproject.DTO.CommentDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<CommentDTO> QueryAllComment(String comment_article_id);

    int AddNewComment(CommentDTO comment);

    int DeleteComment(String comment_id);
}
