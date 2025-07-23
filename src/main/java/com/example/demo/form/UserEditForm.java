package com.example.demo.form;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import com.example.demo.dto.UserEditInfo;
import lombok.Data;

/**
 * ユーザー編集画面Form
 */
@Data
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
