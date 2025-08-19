package com.example.demo.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSendServiceImpl implements MailSendService {
    private final JavaMailSender mailSender;

    @Override
    public boolean sendMail(String mailTo, String mailSubject, String mailText) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(mailTo);
                message.setSubject(mailSubject);
                message.setText(mailText);

                mailSender.send(message);
                return true; // 送信成功
            } catch (Exception e) {
                e.printStackTrace();
                return false; // 送信失敗
            }
    }

}
