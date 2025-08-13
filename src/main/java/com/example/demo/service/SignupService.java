package com.example.demo.service;

import com.example.demo.constant.SignupResult;
import com.example.demo.dto.SignupInfo;


public interface SignupService {
    public SignupResult signup(SignupInfo dto);
}
