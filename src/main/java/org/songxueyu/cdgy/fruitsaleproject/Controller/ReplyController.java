package org.songxueyu.cdgy.fruitsaleproject.Controller;

import org.songxueyu.cdgy.fruitsaleproject.DTO.ArticleDTO;
import org.songxueyu.cdgy.fruitsaleproject.DTO.ReplyDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Article;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Reply;
import org.songxueyu.cdgy.fruitsaleproject.Response.MyResponse;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.ReplyService;
import org.songxueyu.cdgy.fruitsaleproject.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reply")
public class ReplyController {


    @Autowired
    private ReplyService replyService;

    @RequestMapping("/addnewreply")
    @ResponseBody
    public MyResponse AddNewReply(@RequestBody ReplyDTO reply ){

        int i = replyService.AddReply(reply);
        if (i!=0) return MyResponse.builder()
                .code("200")
                .msg("回复成功").build();
        else return MyResponse.builder()
                .code("201")
                .msg("回复失败").build();

    }

    @RequestMapping("/deletereply")
    @ResponseBody
    public MyResponse DeleteReply(@RequestParam String reply_id ){
        int i = replyService.DeleteReply(reply_id);
        if (i!=0)
            return MyResponse.builder()
                    .code("200")
                    .msg("删除成功")
                    .build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("删除失败").build();

    }

    @RequestMapping("/getunreadreply")
    @ResponseBody
    public MyResponse GetAllUnReadReply(@RequestParam String user_name ){

        List<ReplyDTO> replies = replyService.GetAllUnReadReply(user_name);
        return
                MyResponse.builder()
                        .code("200")
                        .msg("获取成功")
                        .object(replies).build();

    }

    @RequestMapping("/readreply")
    @ResponseBody
    public MyResponse ReadReply(@RequestParam String reply_id,@RequestParam String reply_comment_id){
        ArticleDTO article = replyService.ReadReply(reply_id,reply_comment_id);
        return MyResponse.builder()
                .code("200")
                .msg("回复读取成功")
                .object(article).build();

    }
    @ResponseBody
    @RequestMapping("/readall")
    public MyResponse ReadAllReply(@RequestBody List<String> replyIdList){

        replyService.ReadAllReply(replyIdList);
        return MyResponse.builder()
                .code("200")
                .msg("已读全部成功")
                .build();

    }
}
