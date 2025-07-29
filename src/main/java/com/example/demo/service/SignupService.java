package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;

import java.util.Optional;

public interface SignupService {
    public Optional<UserInfo> registerUserInfo(SignupForm form);
}
