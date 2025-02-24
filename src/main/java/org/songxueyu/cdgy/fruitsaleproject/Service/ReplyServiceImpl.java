package org.songxueyu.cdgy.fruitsaleproject.Service;

import org.songxueyu.cdgy.fruitsaleproject.DTO.ArticleDTO;
import org.songxueyu.cdgy.fruitsaleproject.DTO.ReplyDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Article;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Reply;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.ArticleMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.CommentMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.ReplyMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.UserMapper;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.ReplyService;
import org.songxueyu.cdgy.fruitsaleproject.Util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public int AddReply(ReplyDTO reply){
        reply.setReply_dto_id(UuidUtil.getUuid());
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        reply.setReply_dto_time(sdFormat.format(date));
        String comment_id=reply.getReply_dto_comment_id();
        String article_id=commentMapper.GetCommentArticle_id(comment_id);
        int replyNum = articleMapper.GetArticleReplyNum(article_id);
        replyNum = replyNum + 1;
        articleMapper.UpdateArticleReplyNum(replyNum,article_id);


        return replyMapper.AddNewReply(reply);

    }

    @Override
    public int DeleteReply(String reply_id){
        String comment_id=replyMapper.GetReplyCommentId(reply_id);
        String article_id=commentMapper.GetCommentArticle_id(comment_id);
        int replyNum = articleMapper.GetArticleReplyNum(article_id);
        replyNum = replyNum - 1;
        articleMapper.UpdateArticleReplyNum(replyNum,article_id);
        return replyMapper.DeleteReply(reply_id);

    }

    @Override
    public List<ReplyDTO> GetAllUnReadReply(String user_name){
        List<ReplyDTO> replies = replyMapper.GetAllUnReadReply(user_name);
        for (ReplyDTO reply:replies){
            reply.setReply_dto_from_img(userMapper.GetUserImgByName(reply.getReply_dto_from()));
        }
        return replies;
    }

    @Override
    public ArticleDTO ReadReply(String reply_id, String comment_id){
        replyMapper.ReadReply("1",reply_id);
        String article_id = commentMapper.GetCommentArticle_id(comment_id);
        ArticleDTO article = articleMapper.QueryArticleById(article_id);
        article.setArticle_dto_user_img("/api"+userMapper.GetUserImgByName(article.getArticle_dto_user_name()));
        return article;
    }
    @Override
    public void ReadAllReply(List<String> replyIdList){
        for (String string:replyIdList) replyMapper.ReadReply("1",string);
    }
}
