package org.songxueyu.cdgy.fruitsaleproject.Service;

import org.songxueyu.cdgy.fruitsaleproject.DTO.CommentDTO;
import org.songxueyu.cdgy.fruitsaleproject.DTO.ReplyDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Comment;;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Reply;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.ArticleMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.CommentMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.ReplyMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.UserMapper;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.CommentService;
import org.songxueyu.cdgy.fruitsaleproject.Util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<CommentDTO> QueryAllComment(String comment_article_id){
        List<CommentDTO> commentList = commentMapper.QueryAllCommentByArticleId(comment_article_id);

        for (CommentDTO comment:commentList){
            List<ReplyDTO> replies = replyMapper.GetAllReplyById(comment.getComment_dto_id());
            for (ReplyDTO reply:replies){

                reply.setReply_dto_from_img( userMapper.GetUserImgByName(reply.getReply_dto_from()));
            }
            comment.setComment_dto_input_show(false);
            comment.setComment_dto_reply_list(replies);
            comment.setComment_dto_user_img( userMapper.GetUserImgByName(comment.getComment_dto_user_name()));
            comment.setComment_dto_box_show(false);
        }
        return commentList;

    }

    @Override
    public int AddNewComment(CommentDTO comment){
        comment.setComment_dto_id(UuidUtil.getUuid());
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date comment_time = new Date();

        comment.setComment_dto_time(sdFormat.format(comment_time).toString());


        int flag = commentMapper.InsertComment(comment);
        int replyNum = articleMapper.GetArticleReplyNum(comment.getComment_dto_article_id());
        replyNum = replyNum + 1;
        articleMapper.UpdateArticleReplyNum(replyNum,comment.getComment_dto_article_id());
        return flag;
    }

    @Override
    public int DeleteComment(String comment_id){
        int num = replyMapper.DeleteReplyByComment(comment_id);
        String article_id = commentMapper.GetCommentArticle_id(comment_id);
        articleMapper.UpdateArticleReplyNum(num+1,article_id);

        return commentMapper.deleteCommentById(comment_id);
    }
}
