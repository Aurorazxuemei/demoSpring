package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
                .formLogin(login -> login
                          .loginPage("/login")
                          .usernameParameter("loginId")          // 自定义登录画面
                          .defaultSuccessUrl("/menu")   // 登录成功后跳转
                );
        return http.build();
    }
}
