package com.example.demo.config;

import com.example.demo.constant.UrlConst;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    /**パスワードエンコーダー*/
    private final PasswordEncoder passwordEncoder;
    /**ユーザー情報取得Service*/
    private final UserDetailsService userDetailsService;
    /**メッセージ取得*/
    private final MessageSource messageSource;
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
                          .defaultSuccessUrl(UrlConst.MENU))  // 登录成功后跳转
                          //.failureUrl("/login?error")

                .logout(logout ->logout.logoutSuccessUrl(UrlConst.SIGNUP));

        return http.build();
    }
    /**
     * Provider定義
     *
     * @returnカスタマイズProvider情報
     */
    @Bean
    AuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setMessageSource(messageSource);
        return provider;
    }
}
