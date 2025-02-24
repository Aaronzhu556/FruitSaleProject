package org.songxueyu.cdgy.fruitsaleproject.Service;

import org.songxueyu.cdgy.fruitsaleproject.DTO.ArticleDTO;
import org.songxueyu.cdgy.fruitsaleproject.DTO.UserDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Article;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.songxueyu.cdgy.fruitsaleproject.Entity.User;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.ArticleMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.FollowMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.UserMapper;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.UserService;
import org.songxueyu.cdgy.fruitsaleproject.Util.ImageUtil;
import org.songxueyu.cdgy.fruitsaleproject.Util.PasswordUtil;
import org.songxueyu.cdgy.fruitsaleproject.Util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Value("${fruit.image.path}")
    private String imgPath;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private FollowMapper followMapper;

    @Override
    public User getUserByName(String user_name) {
      return userMapper.getUserByName(user_name);
    }

    @Override
    public List<String> getAllUserName() {
        return userMapper.getAllUserName();
    }

    @Override
    public Integer addUser(User user) {
        user.setUser_id(UuidUtil.getUuid());
        user.setUser_password(new BCryptPasswordEncoder().encode(user.getUser_password()));
        return userMapper.addUser(user);
    }
    @Override
    public String UploadUserImg(MultipartFile multipartFile, String user_name){
        String user_img= ImageUtil.uploadImg(multipartFile,imgPath,user_name);
        userMapper.UploadUserImg(user_name,"/api"+user_img);
        return user_img;
    }
    @Override
    public String QueryUserImg(String user_name){
        return userMapper.GetUserImgByName(user_name);
    }

    @Override
    public int EditUserInfo(User user) {
        return userMapper.UpdateUserInfo(user);
    }

    @Override
    public User GetUserInfo(String user_name) {
        return userMapper.QuerybyName(user_name);
    }

    @Override
    public List<User> getAllUser(QueryInfo queryInfo) {
        List<User> userList = new LinkedList<>();
        if (!queryInfo.getQuerytext().equals("")){
            userList = userMapper.getAllUserByName(queryInfo.getQuerytext());
        }else {
            userList = userMapper.getAllUser();
        }
       // for (User user:userList) user.setUser_img(user.getUser_img());
        return userList;
    }

    @Override
    public int updateUserStatus(String user_id, String user_status) {
        return userMapper.updateUserStatus(user_id,user_status);
    }

    @Override
    public List<UserDTO> getHotUser() {

        List<User> userList = userMapper.getAllUser();
        List<ArticleDTO> articles = articleMapper.QueryAllArticle();
        List<ArticleDTO> temp = articles.stream().collect(Collectors.toList());
        List<UserDTO> userDTOList = new LinkedList<>();
        for (User user:userList){
            int user_article_num = articleMapper.GetAllArticleByUser(user.getUser_name()).size();
            int user_fans = followMapper.GetUserFans(user.getUser_name()).size();
            int user_like=0;
            for (int i=0;i<temp.size();i++){
                if (temp.get(i).getArticle_dto_user_name()==user.getUser_name()) user_like = user_like +1;
            }
//            user.setUser_hot(user_article_num+user_fans+user_like);
//            user.setUser_img(user.getUser_img());
//            user.setUser_fans(user_fans);
//            user.setUser_great(user_like);
//            user.setUser_article_num(user_article_num);
            userDTOList.add(UserDTO.builder()
                    .user_dto_id(user.getUser_id())
                    .user_dto_name(user.getUser_name())
                    .user_dto_password("")
                    .user_dto_img(user.getUser_img())
                    .user_dto_status(user.getUser_status())
                    .user_dto_phone(user.getUser_phone())
                    .user_dto_email(user.getUser_email())
                    .user_dto_fans(user_fans)
                    .user_dto_following(user.getUser_following())
                    .user_dto_privacy(user.getUser_privacy())
                    .user_dto_motto(user.getUser_motto())
                    .user_dto_hot(user_article_num+user_fans+user_like)
                    .user_dto_great(user_like)
                    .user_dto_article_num(user_article_num).build());
        }
        userDTOList.sort(Comparator.comparing(UserDTO::getUser_dto_hot).reversed());
        return userDTOList;
    }
}
