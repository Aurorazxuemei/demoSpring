package com.example.demo.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SignupForm {
    /**ログインID*/
    @Length(min=8,max=20)
    private String loginId;
    /**パスワード*/
    @Length(min=8,max=20)
    private String password;
}
