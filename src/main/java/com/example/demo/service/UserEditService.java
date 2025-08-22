package com.example.demo.service;

import com.example.demo.dto.UserEditResult;
import com.example.demo.dto.UserUpdateInfo;
import com.example.demo.entity.UserInfo;

import java.util.Optional;

/**
 * ユーザー編集画面Serviceインターフェース
 */
public interface UserEditService {

   //public UserInfo searchUserInfo(String loginId);
   public Optional<UserInfo> searchUserInfo(String loginId);

   public UserEditResult updateUserInfo(UserUpdateInfo updateDto);
}
