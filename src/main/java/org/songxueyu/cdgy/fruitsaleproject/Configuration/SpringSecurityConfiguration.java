package org.songxueyu.cdgy.fruitsaleproject.Configuration;

import org.songxueyu.cdgy.fruitsaleproject.Security.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;
import java.util.Arrays;

/**
 * @author song
 * */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * 自定义访问控制，默认是所有访问都要经过认证。
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().logoutUrl("/auth/user/logout").logoutSuccessUrl("/").permitAll();
        http.cors().and().authorizeRequests()
                // 放行所有OPTIONS请求
                // 放行登录方法
                .antMatchers("/auth/user/register").permitAll()
                .antMatchers("/auth/user/login").permitAll()
                .antMatchers("/auth/manager/login").permitAll()
                .antMatchers("/auth/user/logout").permitAll()
                .antMatchers("/user/getallusername").permitAll()
                .antMatchers("/path/*.jpg").permitAll()
                // 其他请求都需要认证后才能访问
                .anyRequest().authenticated()
                // 使用自定义的 accessDecisionManager
                .and()
                // 添加未登录与权限不足异常处理器
                // 将自定义的JWT过滤器放到过滤链中
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                // 关闭CSRF
                .csrf().disable()
                // 关闭Session机制
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        // 忽略注册接口
        return (web) -> web.ignoring().antMatchers("/**");
    }
//    @Override
//    public void configure(WebSecurity web){
//        web.ignoring().antMatchers("/**");
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Filter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }


}
