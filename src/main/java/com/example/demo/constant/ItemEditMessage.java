package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemEditMessage {
    /**更新成功*/
    SUCCEED(MessageConst.ITEMEDIT_UPDATE_SUCCEED),
    /**更新失敗*/
    FAILED(MessageConst.ITEMEDIT_UPDATE_FAILED);
    /**メッセージID*/
    private String messageId;
}
