package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserEditMessage {
    /**更新成功*/
    SUCCEED(MessageConst.USEREDIT_UPDATE_SUCCEED),
    /**更新失敗*/
    FAILED(MessageConst.USEREDIT_UPDATE_FAILED);
    /**メッセージID*/
    private String messageId;
}
