package org.songxueyu.cdgy.fruitsaleproject.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.songxueyu.cdgy.fruitsaleproject.Entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserByName(String user_name);
    List<String> getAllUserName();
    Integer addUser(User user);
    int UploadUserImg(String user_name , String user_img);

    String GetUserImgByName(String user_name);
    int UpdateUserInfo(User user);
    User QuerybyName(String user_name);
    List<User> getAllUser();
    List<User> getAllUserByName(String user_name);
    int updateUserStatus(String user_id , String user_status);
    String getUserStatus(String user_name);

}
