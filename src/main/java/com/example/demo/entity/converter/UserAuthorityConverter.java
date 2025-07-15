package com.example.demo.entity.converter;

import com.example.demo.constant.AuthorityKind;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserAuthorityConverter implements AttributeConverter<AuthorityKind, String> {

    @Override
    public String convertToDatabaseColumn(AuthorityKind authorityKind) {
        if (authorityKind == null) {
            return null;
        }
        return authorityKind.getCode();
    }

//    @Override
//    public AuthorityKind convertToEntityAttribute(String code) {
//        if ("1".equals(code)) {
//            return AuthorityKind.ITEM_WATCHER;
//        } else if ("2".equals(code)) {
//            return AuthorityKind.ITEM_MANAGER;
//        } else if ("3".equals(code)) {
//            return AuthorityKind.ITEM_AND_USER_MANAGER;
//        } else return AuthorityKind.UNKNOWN;
//
//    }
    @Override
    public AuthorityKind convertToEntityAttribute(String code) {
        return AuthorityKind.from(code);
    }

}
