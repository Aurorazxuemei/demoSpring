package com.example.demo.constant;

import lombok.Getter;

@Getter
public enum AuthorityKind {
    UNKNOWN("","登録内容が不正"),

    ITEM_WATCHER("1", "商品情報の確認が可能"),

    ITEM_MANAGER("2", "商品情報の確認、更新が可能"),

    ITEM_AND_USER_MANAGER("3", "商品情報の確認、更新、全ユーザー情報の管理が可能");
    /** コード値 */
    private String code;

    /** 画面表示する説明文 */
    private String displayValue;

    AuthorityKind(String code, String displayValue) {
        this.code = code;
        this.displayValue = displayValue;
    }

    public static AuthorityKind from(String code) {
        for (AuthorityKind item : AuthorityKind.values()) {
            if (code.equals(item.code)) {
                return item;
            }
        }
        return AuthorityKind.UNKNOWN;
    }
}
