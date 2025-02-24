package org.songxueyu.cdgy.fruitsaleproject.Controller;

import lombok.extern.slf4j.Slf4j;
import org.songxueyu.cdgy.fruitsaleproject.DTO.UserDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.songxueyu.cdgy.fruitsaleproject.Entity.User;
import org.songxueyu.cdgy.fruitsaleproject.Response.MyResponse;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.UserService;
import org.songxueyu.cdgy.fruitsaleproject.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getallusername")
    @ResponseBody
    public MyResponse getAllUser() {
        return MyResponse.builder()
                .code("200")
                .msg("获取所有用户名成功")
                .object(userService.getAllUserName()).build();
    }
    @ResponseBody
    @RequestMapping("/uploadimg")
    public MyResponse UploadUserImg(@RequestParam("user_img") MultipartFile multipartFile, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String s = userService.UploadUserImg(multipartFile, JwtUtil.getUserAccountFromToken(token));
        if (s != null)
            return MyResponse.builder()
                    .code("200")
                    .msg("上传头像成功")
                    .info(s).build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("上传头像失败")
                    .build();

    }

    @ResponseBody
    @RequestMapping("/queryuserimg")
    public MyResponse QueryUserImg(@RequestParam String user_name){
        String userImg = userService.QueryUserImg(user_name);
        return MyResponse.builder()
                .code("200")
                .msg("查询成功")
                .info(userImg).build();
    }
    @ResponseBody
    @RequestMapping("/updateuserinfo")
    public MyResponse EditUserInfo(@RequestBody User user, @RequestHeader("Authorization")String token){

        userService.EditUserInfo(user);
        return MyResponse.builder()
                .code("200")
                .msg("用户信息更新成功")
                .build();

    }
    @ResponseBody
    @RequestMapping("/queryuserinfo")
    public MyResponse GetUserInfo(@RequestParam String user_name,@RequestHeader("Authorization")String token) {

        User user = userService.GetUserInfo(user_name);
        return MyResponse.builder()
                .code("200")
                .msg("查询成功")
                .object(user).build();
    }
    @RequestMapping("/getalluser")
    @ResponseBody
    public MyResponse getAllUser(@RequestBody QueryInfo queryInfo){
        List<User> allUser = userService.getAllUser(queryInfo);
        if (!allUser.isEmpty())
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .object(allUser)
                    .info(String.valueOf(allUser.size())).build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("暂无数据").build();
    }
    @RequestMapping("/changestatus")
    @ResponseBody
    public MyResponse updateUserStatus(@RequestParam String user_id,@RequestParam String user_status){
        int i = userService.updateUserStatus(user_id, user_status);
        if (i!=0) return MyResponse.builder()
                .code("200")
                .msg("修改用户状态成功")
                .build();
        else return MyResponse.builder()
                .code("201")
                .msg("修改失败").build();
    }
    @RequestMapping("/getallusernum")
    @ResponseBody
    public MyResponse getAllUserNum(){
        return MyResponse.builder()
                .code("200")
                .msg("查询成功")
                .info(String.valueOf(userService.getAllUserName().size())).build();
    }
    @RequestMapping("/gethotuser")
    @ResponseBody
    public MyResponse getHotUser(){
        List<UserDTO> hotUser = userService.getHotUser();
        return MyResponse.builder()
                .code("200")
                .msg("查询成功")
                .object(hotUser)
                .info(String.valueOf(hotUser.size())).build();
    }

}
