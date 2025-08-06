package com.example.demo.util;

import org.springframework.context.MessageSource;

import java.util.Locale;
/**
 *
 * アプリケーション共通処理クラス
 *
 * @author 張雪梅
 *
 */
public class AppUtil {
    public static String addWildcard(String param) {
        return "%" + param + "%";
    }

    /**
     * メッセージIDから、プロパティファイルに定義されているメッセージを取得する。
     *
     * <p>取得したメッセージ内で置換が必要な個所がある場合は<br>
     * 引数の置換文字群を使って置換を行う。
     * @param messageSource　メッセージソース
     * @param messageId　 メッセージID
     * @param params　置換文字群
     * @return　プロパティファイルから取得したメッセージ
     */
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
