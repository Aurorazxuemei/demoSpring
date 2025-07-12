package com.example.demo.entity;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
        private UserStatusKind status;

        private AuthorityKind authority;

        @Column(name = "create_time")
        private LocalDateTime createTime;

        @Column(name = "update_time")
        private LocalDateTime updateTime;
}
