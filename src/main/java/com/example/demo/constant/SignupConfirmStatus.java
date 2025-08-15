package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**ユーザー本登録結果種別*/
@Getter
@AllArgsConstructor
public enum SignupConfirmStatus {

    /* 本更新成功 */
    SUCCEED(MessageConst.SIGNUP_CONFIRM_COMPLETE),

    /* ワンタイムコード誤り */
    FAILURE_BY_WRONG_ONE_TIME_CODE(MessageConst.SIGNUP_CONFIRM_WRONG_ONE_TIME_CODE),

    /* ワンタイムコード有効期限切れ */
    FAILURE_BY_EXPIRED_ONE_TIME_CODE(MessageConst.SIGNUP_CONFIRM_EXPIRED_ONE_TIME_CODE);

    String messageId;
}
