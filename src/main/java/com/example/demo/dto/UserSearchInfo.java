package com.example.demo.dto;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


    /**
     * ユーザー一覧画面検索用DTOクラス
     *
     * @author 張
     *
     */
    @Data
    public class UserSearchInfo {

        /** ログインID */
        @Length(min = 8, max = 20)
        private String loginId;

        /** アカウント状態種別 */
        private UserStatusKind userStatusKind;

        /** ユーザー権限種別 */
        private AuthorityKind authorityKind;
}
