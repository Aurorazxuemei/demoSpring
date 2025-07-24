package com.example.demo.util;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class AppUtil {
    public static String addWildcard(String param) {
        return "%" + param + "%";
    }

    public static String getMessage(MessageSource messageSource, String messageId, Object... params) {
        return messageSource.getMessage(messageId, params, Locale.JAPAN);
    }
    /**
     * リダイレクト先のURLを受け取って、リダイレクトURLを作成します
     */
    public static String doRedirect(String url){
        return "redirect:"+url;
    }
}
