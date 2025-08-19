package com.example.demo.service.common;
/**
 * メールを送信します。
 *
 * @param mailTo 宛先
 * @param mailSubject 件名
 * @param mailText 本文
 * @return 送信結果(成功ならtrue)
 */
public interface MailSendService {
    public boolean sendMail(String mailTo, String mailSubject, String mailText);
}
