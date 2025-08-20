package com.example.demo.controller;

import com.example.demo.constant.*;
import com.example.demo.service.LoginService;
import com.example.demo.service.SignupConfirmService;
import com.example.demo.service.SignupService;
import com.example.demo.util.AppUtil;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequiredArgsConstructor
public class SignupConfirmController {
    /** セッションオブジェクト */
    private final HttpSession session;
    private final MessageSource messageSource;
    private final SignupConfirmService service;

    @GetMapping(UrlConst.SIGNUP_CONFIRM)
    public String view() {
        return ViewNameConst.SIGNUP_CONFIRM;
    }

    @PostMapping(UrlConst.SIGNUP_CONFIRM)
    public String signupConfirm(String oneTimeCode, RedirectAttributes redirectAttributes) {
        String loginId = (String) session.getAttribute(SessionKeyConst.ONE_TIME_AUTH_LOGIN_ID);
        if (loginId == null) {
            redirectAttributes.addFlashAttribute(ModelKey.MESSAGE, AppUtil.getMessage(messageSource, MessageConst.SIGNUP_CONFIRM_INVALID_OPERATION));
            redirectAttributes.addFlashAttribute(ModelKey.IS_ERROR, true);
            return AppUtil.doRedirect(UrlConst.SIGNUP_CONFIRM);
        }
        var signupConfirmStatus = service.chkTentativeSignupUser(loginId, oneTimeCode);

        // 次画面にワンタイムコード認証結果の情報を渡す
        var message = AppUtil.getMessage( messageSource,signupConfirmStatus.getMessageId());
        var isError = signupConfirmStatus != SignupConfirmStatus.SUCCEED;
        redirectAttributes.addFlashAttribute(ModelKey.MESSAGE, message);
        redirectAttributes.addFlashAttribute(ModelKey.IS_ERROR, isError);
        if (isError){
            return AppUtil.doRedirect(UrlConst.SIGNUP_CONFIRM);
        }
        session.removeAttribute(SessionKeyConst.ONE_TIME_AUTH_LOGIN_ID);
        return AppUtil.doRedirect(UrlConst.SIGNUP_COMPLETION);
    }

}
