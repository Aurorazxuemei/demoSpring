package com.example.demo.authentication;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserInfoRepository userInfoRepository;
    /**アカウントロックの継続時間*/
    private final int LOCKING_TIME = 1;

    /** アカウントロックを行うログイン失敗回数境界値 */
    private final int LOCKING_BORDER_COUNT = 3;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userInfo = userInfoRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        var accountLockedTime  = userInfo.getAccountLockedTime();
        var isAccountLocked = accountLockedTime != null
                && accountLockedTime.plusHours(LOCKING_TIME).isAfter(LocalDateTime.now());
        return User.withUsername(userInfo.getLoginId())
                .password(userInfo.getPassword())
                .roles("USER")
                .disabled(userInfo.getUserStatusKind().isDisabled())
                .accountLocked(isAccountLocked)
                .build();
    }

    /**
     * 認証失敗時に失敗回数を加算、ロック日時を更新する
     *
     * @param event イベント情報
     */
    @EventListener
    public void handle(AuthenticationFailureBadCredentialsEvent event) {
        var loginId = event.getAuthentication().getName();
        userInfoRepository.findById(loginId).ifPresent(userInfo -> {
            userInfoRepository.save(userInfo.incrementLoginFailureCount());

            var isReachFailureCount = userInfo.getLoginFailureCount() == LOCKING_BORDER_COUNT;
            if (isReachFailureCount) {
                userInfoRepository.save(userInfo.updateAccountLocked());
            }
        });
    }
    /**
     * 認証成功時にログイン失敗回数をリセットする
     *
     * @param event イベント情報
     */
    @EventListener
    public void handle(AuthenticationSuccessEvent event) {
        var loginId = event.getAuthentication().getName();
        userInfoRepository.findById(loginId).ifPresent(userInfo -> {
            userInfoRepository.save(userInfo.resetLoginFailureInfo());
        });
    }
}
