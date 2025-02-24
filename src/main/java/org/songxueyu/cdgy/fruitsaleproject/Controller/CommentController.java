package org.songxueyu.cdgy.fruitsaleproject.Controller;

import org.songxueyu.cdgy.fruitsaleproject.DTO.CommentDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Comment;
import org.songxueyu.cdgy.fruitsaleproject.Response.MyResponse;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.CommentService;
import org.songxueyu.cdgy.fruitsaleproject.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/queryallcomment")
    @ResponseBody
    public MyResponse QueryAllComment(@RequestParam String comment_article_id,@RequestParam int pagesize,@RequestParam int pagenum, @RequestHeader("Authorization") String token) {
        List<CommentDTO> comments = commentService.QueryAllComment(comment_article_id);
        if (!comments.isEmpty()) {
            return MyResponse.builder()
                    .code("200")
                    .msg("加载成功")
                    .info(String.valueOf(comments.size()))
                    .object(comments).build();
        }
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("此帖子现在没有任何回复")
                    .build();
    }


    @RequestMapping("/addnewcomment")
    @ResponseBody
    public MyResponse AddNewComment(@RequestBody CommentDTO comment,@RequestHeader("Authorization") String token){
        int flag=0;
        flag = commentService.AddNewComment(comment);
        if (flag!=0)
            return MyResponse.builder()
                    .code("200")
                    .msg("回复成功").build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("回复失败,请重试").build();
    }


    @RequestMapping("/deletecomment")
    @ResponseBody
    public MyResponse DeleteComment(@RequestParam String comment_id){
        int i = commentService.DeleteComment(comment_id);
        if (i!=0)
            return MyResponse.builder()
                    .code("200")
                    .msg("删除成功").build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("删除失败").build();
    }
}