package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.UserInfoRepository;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

    public final UserInfoRepository repository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserInfo> registerUserInfo(SignupForm form){
        var userInfoExistedOpt =repository.findById(form.getLoginId());
        if(userInfoExistedOpt.isPresent()){
            return Optional.empty();
        }
        var userInfo = mapper.map(form, UserInfo.class);
        String encodedpassword = passwordEncoder.encode(form.getPassword());
        userInfo.setPassword(encodedpassword);
        return Optional.of(repository.save(userInfo));
    }
}
