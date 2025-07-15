package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserListInfo {
    /** ログインID */
    private String loginId;

    /** ログイン失敗回数 */
    private int loginFailureCount;

    /** アカウントロック日時 */
    private LocalDateTime accountLockedTime;

    /** アカウント状態 */
    private String status;

    /** 権限 */
    private String authority;

    /** 登録日時 */
    private LocalDateTime createTime;

    /** 最終更新日時 */
    private LocalDateTime updateTime;
}
