package com.example.demo.config;

import com.example.demo.constant.UrlConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    /**ユーザー名のname属性*/
    private final String USERNAME_PARAMETER = "loginId";
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(UrlConst.NO_AUTHENTICATION).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                          .loginPage(UrlConst.LOGIN)
                          .usernameParameter(USERNAME_PARAMETER)          // 自定义登录画面
                          .defaultSuccessUrl(UrlConst.MENU)   // 登录成功后跳转
                          //.failureUrl("/login?error")
                );
        return http.build();
    }
}
