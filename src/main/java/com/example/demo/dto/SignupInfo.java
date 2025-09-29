package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;





/**ユーザー登録用DTOクラス*/
@Data
@AllArgsConstructor   // 全フィールドを引数に持つコンストラクタ
@NoArgsConstructor    // 引数なしのコンストラクタ
public class SignupInfo {
    /**ログインID*/
    private String loginId;
    /**パスワード*/
    private String password;
    /**メールアドレス*/
    private String mailAddress;
}
