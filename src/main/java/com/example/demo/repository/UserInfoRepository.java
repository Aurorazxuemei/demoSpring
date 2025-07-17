package com.example.demo.repository;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import com.example.demo.dto.UserListInfo;
import com.example.demo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    List<UserInfo> findByLoginIdLikeAndUserStatusKindAndAuthorityKind(String loginId, UserStatusKind uerStatusKind, AuthorityKind authorityKind);
    List<UserInfo> findByLoginIdLikeAndUserStatusKind(String loginId, UserStatusKind uerStatusKind);
    List<UserInfo> findByLoginIdLikeAndAuthorityKind(String loginId, AuthorityKind authorityKind);
    List<UserInfo> findByLoginIdLike(String loginId);

}