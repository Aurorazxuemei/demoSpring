package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserEditServiceImpl implements UserEditService {
    private final UserInfoRepository repository;

    @Override
    public Optional<UserInfo> searchUserInfo(String loginId) {
        return repository.findById(loginId);

    }
}

