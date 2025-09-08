package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemDeleteResult {
    SUCCESS(MessageConst.ITEMLIST_DELETE_SUCCEED),
    ERROR(MessageConst.ITEMLIST_NON_EXISTED_ITEM_ID);
    /**メッセージID*/
    private String messageId;
}
