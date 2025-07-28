package com.example.demo.service;

import com.example.demo.entity.UserInfo;

import java.util.Optional;

public interface LoginService {
    public Optional<UserInfo> searchUserById(String loginId);
}
