package com.example.demo.entity.converter;


import com.example.demo.constant.UserStatusKind;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserStatusConverter implements AttributeConverter<UserStatusKind, Boolean> {

    /**
     * 引数で受け取ったユーザー状態種別Enumを、利用可否のbooleanに変換します。
     *
     * @param ユーザー状態種別Enum
     * @return 引数で受け取ったユーザー状態種別Enumに保管されているboolean
     */

    @Override
    public Boolean convertToDatabaseColumn(UserStatusKind userStatusKind) {
        if (userStatusKind == null) { return null;}
        return userStatusKind.isDisabled();
    }
    /**
     * 引数で受け取った権限種別のコード値を、ユーザー状態種別Enumに変換します。
     *
     * @param 利用可否(利用不可ならtrue)
     * @return 引数で受け取った利用可否から逆引きしたユーザー状態種別Enum
     */
    @Override
    public UserStatusKind convertToEntityAttribute(Boolean isDisabled) {
        return isDisabled ? UserStatusKind.DISABLED : UserStatusKind.ENABLED;
    }
}