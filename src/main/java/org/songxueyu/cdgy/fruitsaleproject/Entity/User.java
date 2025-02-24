package org.songxueyu.cdgy.fruitsaleproject.Entity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String user_id;
    private String user_name;
    private String user_password;
    private String user_phone;
    private String user_img;
    private String user_status; //用于标记user状态 1-正常 0-冻结
    private String user_email;
    private Integer user_fans;
    private Integer user_following;
    private String user_privacy;
    private String user_motto;
}
