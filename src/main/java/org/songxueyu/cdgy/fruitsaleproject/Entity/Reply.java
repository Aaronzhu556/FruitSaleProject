package org.songxueyu.cdgy.fruitsaleproject.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Reply {
    private String reply_id;
    private String reply_from;
    private String reply_to;
    private String reply_content;
    private String reply_time;
    private String reply_comment_id;
    private String reply_status;



}
