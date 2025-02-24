package org.songxueyu.cdgy.fruitsaleproject.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LikeMapper {

    public List<String> GetAllArticleLike(String like_user_name);
}
