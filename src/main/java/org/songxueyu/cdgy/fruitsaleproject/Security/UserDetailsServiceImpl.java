package com.graduationproject.recommendationplatform.Security;

import com.graduationproject.recommendationplatform.Entity.User;
import com.graduationproject.recommendationplatform.Service.Interface.UserService;
import lombok.extern.slf4j.Slf4j;
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
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("获取用户:{}",s);
        User userByAccount = userService.getUserByAccount(s);
        if (userByAccount == null){
            throw new UsernameNotFoundException("User is not existed");

        }

        userByAccount.setUser_password(new BCryptPasswordEncoder().encode(userByAccount.getUser_password()));

        return new UserDetailsImpl(userByAccount);
    }
}
