package org.songxueyu.cdgy.fruitsaleproject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    @JsonProperty("reply_id")
    private String reply_dto_id;
    @JsonProperty("reply_from")
    private String reply_dto_from;
    @JsonProperty("reply_to")
    private String reply_dto_to;
    @JsonProperty("reply_content")
    private String reply_dto_content;
    @JsonProperty("reply_time")
    private String reply_dto_time;
    @JsonProperty("reply_comment_id")
    private String reply_dto_comment_id;
    @JsonProperty("reply_status")
    private String reply_dto_status;
    @JsonProperty("reply_from_img")
    private String reply_dto_from_img;


}
