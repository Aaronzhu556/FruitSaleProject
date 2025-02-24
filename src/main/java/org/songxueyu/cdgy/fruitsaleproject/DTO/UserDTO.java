package org.songxueyu.cdgy.fruitsaleproject.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    @JsonProperty("user_id")
    private String user_dto_id;
    @JsonProperty("user_name")
    private String user_dto_name;
    @JsonProperty("user_password")
    private String user_dto_password;
    @JsonProperty("user_phone")
    private String user_dto_phone;
    @JsonProperty("user_img")
    private String user_dto_img;
    @JsonProperty("user_status")
    private String user_dto_status; //用于标记user状态 1-正常 0-冻结
    @JsonProperty("user_email")
    private String user_dto_email;
    @JsonProperty("user_fans")
    private Integer user_dto_fans;
    @JsonProperty("user_following")
    private Integer user_dto_following;
    @JsonProperty("user_privacy")
    private String user_dto_privacy;
    @JsonProperty("user_motto")
    private String user_dto_motto;
    @JsonProperty("user_hot")
    private int user_dto_hot;
    @JsonProperty("user_article_num")
    private int user_dto_article_num;
    @JsonProperty("user_great")
    private int user_dto_great;
}
