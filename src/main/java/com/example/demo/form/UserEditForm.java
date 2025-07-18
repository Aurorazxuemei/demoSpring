package com.example.demo.form;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;

/**
 * ユーザー編集画面Form
 */
public class UserEditForm {
    /**
     * ログイン失敗状況をリセットするか(リセットするならtrue)
     */
    private boolean resetsLoginFailure;
    /** アカウント状態種別 */
    private UserStatusKind userStatusKind;

    /** ユーザー権限種別 */
    private AuthorityKind authorityKind;
}