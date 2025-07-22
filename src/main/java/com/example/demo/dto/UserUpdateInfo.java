package com.example.demo.dto;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import lombok.Data;

@Data
public class UserUpdateInfo {
    /** ログインID */
    private String loginId;

    /** ログイン失敗状況をリセットするか(リセットする場合、true) */
    private boolean resetsLoginFailure;

    /** アカウント状態種別 */
    private UserStatusKind userStatusKind;

    /** ユーザー権限種別 */
    private AuthorityKind authorityKind;
}
