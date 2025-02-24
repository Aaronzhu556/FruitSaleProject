package org.songxueyu.cdgy.fruitsaleproject.Controller;


import lombok.extern.slf4j.Slf4j;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Manager;
import org.songxueyu.cdgy.fruitsaleproject.Entity.User;
import org.songxueyu.cdgy.fruitsaleproject.Response.MyResponse;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.ManagerService;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.UserService;
import org.songxueyu.cdgy.fruitsaleproject.Service.UserServiceImpl;
import org.songxueyu.cdgy.fruitsaleproject.Util.JwtUtil;
import org.songxueyu.cdgy.fruitsaleproject.Util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
@Slf4j
@CrossOrigin
public class AuthController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @RequestMapping("/manager/login")
    @ResponseBody
    public MyResponse managerLogin(@RequestBody Manager manager , HttpServletResponse response){
        String code = managerService.managerLogin(manager);

        if (code.equals("200")) {
            response.setHeader("token", JwtUtil.createToken(manager.getManager_name(),"manager"));
            return MyResponse.builder()
                    .code(code)
                    .msg("登录成功")
                    .build();
        }
        else if (code.equals("201"))
            return MyResponse.builder()
                .code(code)
                .msg("管理员密码错误，请重新输入")
                .object(null).build();
        else return MyResponse.builder()
                    .code(code)
                    .msg("没有此管理员")
                    .object(null).build();
    }

    @RequestMapping("/user/login")
    @ResponseBody
    public MyResponse userLogin(@RequestBody User user , HttpServletResponse response){
        //UsernamePasswordAuthenticationFilter authenticationFilter= new UsernamePasswordAuthenticationFilter(user.getUser_name(),user.getUser_password());
        log.info("========进入登录流程============");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUser_name(),user.getUser_password());
        log.info("authenticationToken:{}", authenticationToken);
        try{
            authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){
            log.info("{}",e);
            return MyResponse.builder()
                    .code("201")
                    .msg("用户名或者密码错误")
                    .object(null).build();
        }
        //保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = JwtUtil.createToken(user.getUser_name(),"user");
        log.info("token:{}",token);
        response.setHeader("token", token);
        return MyResponse.builder()
                .code("200")
                .msg("登录成功")
                .info(user.getUser_name())
                .object(token).build();
    }
    @RequestMapping("/user/logout")
    @ResponseBody
    public MyResponse userLogout(HttpServletRequest request,HttpServletResponse response){
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        // 执行注销操作，清除安全上下文等相关信息
        logoutHandler.logout(request, response, null);
        return MyResponse.builder()
                .code("200")
                .msg("注销成功").build();
    }
    @RequestMapping("/user/register")
    @ResponseBody
    public MyResponse userRegister(@RequestBody User user , HttpServletResponse response){
        log.info("================用户进入注册流程==================");
        if (userService.addUser(user)==1){
            return MyResponse.builder()
                    .code("200")
                    .msg("注册成功")
                    .object(null).build();
        }
        else return MyResponse.builder()
                .code("201")
                .msg("注册失败")
                .object(null).build();
    }
    @RequestMapping("/user/test")
    @ResponseBody
    public MyResponse test(@RequestBody User user , HttpServletResponse response){
        log.info("=========用户进入测试流程=========");
        return MyResponse.builder().build();
    }
}
