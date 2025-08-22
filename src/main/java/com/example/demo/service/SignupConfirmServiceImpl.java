package com.example.demo.service;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.SignupConfirmStatus;
import com.example.demo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignupConfirmServiceImpl implements SignupConfirmService {

    public final UserInfoRepository userInfoRepository;
    public final PasswordEncoder passwordEncoder;
    private static final long CODE_EXPIRATION_MINUTES = 3;

    @Override
    public SignupConfirmStatus updateUserAsSignupCompletion(String loginId, String oneTimeCode) {
        var userOpt = userInfoRepository.findById(loginId);
        if (userOpt.isEmpty()) {
            return SignupConfirmStatus.FAILURE_BY_NOT_EXISTS_TENTATIVE_USER;
        }
        var updateUserInfo = userOpt.get();
        if (!passwordEncoder.matches(oneTimeCode, updateUserInfo.getOneTimeCode())) {
            return SignupConfirmStatus.FAILURE_BY_WRONG_ONE_TIME_CODE;
        }
        LocalDateTime sendTime = updateUserInfo.getOneTimeCodeSendTime();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(sendTime, now);
        if (duration.toMinutes() >= CODE_EXPIRATION_MINUTES) {
            return SignupConfirmStatus.FAILURE_BY_EXPIRED_ONE_TIME_CODE;
        }
        updateUserInfo.setSignupCompleted(true);
        updateUserInfo.setOneTimeCode(null);
        updateUserInfo.setOneTimeCodeSendTime(null);
        updateUserInfo.setUpdateTime(LocalDateTime.now());
        updateUserInfo.setUpdateUser(loginId);
        try {
            userInfoRepository.save(updateUserInfo);
        } catch (Exception e) {
            return SignupConfirmStatus.FAILURE_BY_DB_ERROR;
        }
        return SignupConfirmStatus.SUCCEED;
    }

}
