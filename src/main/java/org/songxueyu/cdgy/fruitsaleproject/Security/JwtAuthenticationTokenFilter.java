package org.songxueyu.cdgy.fruitsaleproject.Security;

import org.songxueyu.cdgy.fruitsaleproject.Util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final static String AUTH_HEADER = "Authorization";

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getServletPath();
        log.info("=============requestPath为=============="+requestPath);
        if ("/user/getallusername".equals(requestPath)) {
            filterChain.doFilter(request,response);
            return;
        }
        if ("/auth/user/register".equals(requestPath)) {
            filterChain.doFilter(request,response);
            return;
        }
        if ("/auth/user/login".equals(requestPath)) {
            filterChain.doFilter(request,response);
            return;
        }
        if ("/auth/manager/login".equals(requestPath)) {
            filterChain.doFilter(request,response);
            log.info("我进来了，/auth/manager/login");
            return;
        }
//        if (requestPath.startsWith("/path/")) {
//            filterChain.doFilter(request,response);
//            log.info("我进来了"+requestPath);
//            return;
//        }
        log.info("Jwt过滤器启动....");
        String token = request.getHeader(AUTH_HEADER);
        if (!Objects.isNull(token)){
            log.info("authToken:{}",token);
            if (JwtUtil.verifyToken(token)){
                log.info("有效token");
                final String user_name = (String) JwtUtil.getInfoFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                // 将authentication信息放入到上下文对象中
                SecurityContextHolder.getContext().setAuthentication(authentication);


            }
        }
        filterChain.doFilter(request,response);

    }
}
