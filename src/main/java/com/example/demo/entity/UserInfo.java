package com.example.demo.entity;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import com.example.demo.entity.converter.UserAuthorityConverter;
import com.example.demo.entity.converter.UserStatusConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


    @Entity
    @Table(name = "user_info", schema = "spring_dev")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserInfo {
        @Id
        @Column(name = "login_id")
        private String loginId;

        @Column(name = "password")
        private String password;

        @Column(name = "login_failure_count")
        private int loginFailureCount;

        @Column(name = "account_locked_time")
        private LocalDateTime accountLockedTime;

        @Column(name = "is_disabled")
        @Convert(converter = UserStatusConverter.class)
        private UserStatusKind userStatusKind;

        @Column(name = "authority")
        @Convert(converter = UserAuthorityConverter.class)
        private AuthorityKind authorityKind;

        @Column(name = "create_time")
        private LocalDateTime createTime;

        @Column(name = "update_time")
        private LocalDateTime updateTime;
}
