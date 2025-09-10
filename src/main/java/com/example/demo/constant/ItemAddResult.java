package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**商品登録結果種別*/
@Getter
@AllArgsConstructor
public enum ItemAddResult {
    /* エラーなし */
    SUCCEED(MessageConst.ITEM_ADD_SUCCEED),

    /* 既に本登録済み */
    FAILURE_BY_ALREADY_COMPLETED(MessageConst.ITEM_ALREADY_COMPLETED),

    /* DB更新エラー */
    FAILURE_BY_DB_ERROR(MessageConst.ITEM_DB_ERROR);

    String messageId;

}
