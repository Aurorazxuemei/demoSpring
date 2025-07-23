package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserEditInfo {
    private String loginId;
    private String loginFailureCount;
    private LocalDateTime accountLockedTime;
}
