package org.songxueyu.cdgy.fruitsaleproject.Service.Interface;

import org.songxueyu.cdgy.fruitsaleproject.DTO.ArticleDTO;
import org.songxueyu.cdgy.fruitsaleproject.DTO.ReplyDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Article;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Reply;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReplyService {
    public int AddReply(ReplyDTO reply);
    public int DeleteReply(String reply_id);
    public List<ReplyDTO>GetAllUnReadReply(String user_name);
    public ArticleDTO ReadReply(String reply_id, String comment_id);
    public void ReadAllReply(List<String> replyIdList);
}
