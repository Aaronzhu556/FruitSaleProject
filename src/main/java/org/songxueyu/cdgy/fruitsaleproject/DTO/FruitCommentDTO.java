package org.songxueyu.cdgy.fruitsaleproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FruitCommentDTO {
    private String fruit_comment_id;
    private String fruit_comment_content;
    private String fruit_comment_date;
    private String fruit_comment_user_name;
    private String fruit_comment_user_img;
    private String fruit_id;
    private int fruit_comment_star;
}
