package org.songxueyu.cdgy.fruitsaleproject.Security;

import lombok.extern.slf4j.Slf4j;
import org.songxueyu.cdgy.fruitsaleproject.DTO.NamePasswordValidate;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Manager;
import org.songxueyu.cdgy.fruitsaleproject.Entity.User;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.UserMapper;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.ManagerService;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.UserService;
import org.songxueyu.cdgy.fruitsaleproject.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private ManagerService managerService;
    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        log.info("获取用户token:{}",token);
        NamePasswordValidate namePasswordValidate = new NamePasswordValidate();
        if (!JwtUtil.verifyToken(token)){  //说明不是合法的token，是用户名字
            log.info("我进来了！！！！！！！！！！！");
            User userByName = userService.getUserByName(token);

            if (userByName == null || userByName.getUser_status().equals("0")){
                throw new UsernameNotFoundException("用户不存在或者用户被冻结");
            }


            namePasswordValidate.setName(token);
            namePasswordValidate.setPassword(userByName.getUser_password());
            namePasswordValidate.setRole("user");
        }else {
            String user_account = JwtUtil.getUserAccountFromToken(token);
            String user_role = JwtUtil.getUserRoleFromToken(token);

            if (user_role.equals("user")){
                User userByName = userService.getUserByName(user_account);
                namePasswordValidate.setName(userByName.getUser_name());
                namePasswordValidate.setPassword(userByName.getUser_password());
                namePasswordValidate.setRole("user");
            }
            else {
                Manager managerByName = managerService.getManagerByName(user_account);
                namePasswordValidate.setName(managerByName.getManager_name());
                namePasswordValidate.setPassword(managerByName.getManager_password());
                namePasswordValidate.setRole("manager");
            }
        }
        if (namePasswordValidate == null){
            throw new UsernameNotFoundException("用户不存在");

        }
        log.info("用户信息为：:{}",namePasswordValidate);
        //userByName.setUser_password(new BCryptPasswordEncoder().encode(userByName.getUser_password()));

        return new UserDetailsImpl(namePasswordValidate);
    }
}
