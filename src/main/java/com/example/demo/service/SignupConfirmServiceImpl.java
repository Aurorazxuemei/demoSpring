package com.example.demo.service;

import com.example.demo.constant.SignupConfirmStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class SignupConfirmServiceImpl implements SignupConfirmService {
    @Override
    public SignupConfirmStatus chkTentativeSignupUser(String loginId, String oneTimeCode) {
        return SignupConfirmStatus.SUCCEED;
    }

}
