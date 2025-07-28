package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    public final UserInfoRepository repository;

    @Override
    public Optional<UserInfo> searchUserById(String loginId){
        return repository.findById(loginId);
    }
}
