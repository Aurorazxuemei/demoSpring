package com.example.demo.service;

import com.example.demo.constant.SignupConfirmStatus;

public interface SignupConfirmService {
    SignupConfirmStatus updateUserAsSignupCompletion(String loginId, String oneTimeCode);
}
