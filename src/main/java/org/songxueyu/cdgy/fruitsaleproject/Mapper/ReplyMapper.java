package org.songxueyu.cdgy.fruitsaleproject.Mapper;

import org.apache.ibatis.annotations.*;
import org.songxueyu.cdgy.fruitsaleproject.DTO.ReplyDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Reply;

import java.util.List;

@Mapper
public interface ReplyMapper {

    public List<ReplyDTO> GetAllReplyById(String reply_comment_id);

    public int AddNewReply(ReplyDTO reply);

    public int DeleteReply(String reply_id);


    public int DeleteReplyByComment(String reply_comment_id);


    public String GetReplyCommentId(String reply_id);


    public List<ReplyDTO> GetAllUnReadReply(String user_name);


    public int ReadReply(String reply_status,String reply_id);
}
