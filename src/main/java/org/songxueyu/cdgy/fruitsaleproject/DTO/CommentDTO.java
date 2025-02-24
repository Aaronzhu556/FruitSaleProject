package org.songxueyu.cdgy.fruitsaleproject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Reply;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    @JsonProperty("comment_id")
    private String comment_dto_id;
    @JsonProperty("comment_user_name")
    private String comment_dto_user_name;
    @JsonProperty("comment_time")
    private String comment_dto_time;
    @JsonProperty("comment_content")
    private String comment_dto_content;
    @JsonProperty("comment_article_id")
    private String comment_dto_article_id;

    @JsonProperty("comment_input_show")
    private boolean comment_dto_input_show;
    @JsonProperty("comment_user_img")
    private String comment_dto_user_img;
    @JsonProperty("comment_reply_list")
    private List<ReplyDTO> comment_dto_reply_list = new LinkedList<>();
    @JsonProperty("comment_box_show")
    private boolean comment_dto_box_show;

    public void setComment_dto_reply_list(List<ReplyDTO> comment_dto_reply_list) {
        this.comment_dto_reply_list = comment_dto_reply_list;
    }
}
