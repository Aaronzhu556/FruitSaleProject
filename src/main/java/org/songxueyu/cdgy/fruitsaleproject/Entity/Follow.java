package org.songxueyu.cdgy.fruitsaleproject.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Follow {
    private String follow_id;
    private String follow_user_from;
    private String follow_user_to;




}
