package com.example.demo.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    /**メールアドレス*/
    @Length(max=100)
    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "{signup.invalidMailAddress}")
    private String mailAddress;
}
