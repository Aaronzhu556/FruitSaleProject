package org.songxueyu.cdgy.fruitsaleproject.Service.Interface;

import org.songxueyu.cdgy.fruitsaleproject.DTO.UserDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.songxueyu.cdgy.fruitsaleproject.Entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {
    public User getUserByName (String user_name);
    public List<String> getAllUserName();
    public Integer addUser(User user);
    public String UploadUserImg(MultipartFile multipartFile, String user_name);
    public String QueryUserImg(String user_name);
    public int EditUserInfo(User user);
    public User GetUserInfo(String user_name);
    public List<User> getAllUser(QueryInfo queryInfo);
    public int updateUserStatus(String user_id, String user_status);
    public List<UserDTO> getHotUser();
}
