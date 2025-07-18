package com.example.demo.service;

import com.example.demo.entity.UserInfo;

import java.util.Optional;

public interface UserEditService {

   //public UserInfo searchUserInfo(String loginId);
   public Optional<UserInfo> searchUserInfo(String loginId);
}
