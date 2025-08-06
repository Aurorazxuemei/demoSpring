package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Bean定義クラス
 *
 * @author 張雪梅
 *
 */
@Configuration
public class BeanDefine {
    /**
     * パスワードエンコーダーのBean定義を行います。
     * @return　パスワードエンコーダー(BCrypt形式)
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
