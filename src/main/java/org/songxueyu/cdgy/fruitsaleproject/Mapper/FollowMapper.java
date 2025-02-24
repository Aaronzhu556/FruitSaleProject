package org.songxueyu.cdgy.fruitsaleproject.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FollowMapper {
    //查询关注者

    public List<String> GetUserFollowing(String follow_user_from);
    //查询粉丝

    public List<String> GetUserFans(String follow_user_to);
    //插入关注记录

    public int FollowUser(String follow_id,String follow_user_from,String follow_user_to);


    public int UnFollowUser(String follow_user_from,String follow_user_to);
}
