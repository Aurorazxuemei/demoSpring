package com.example.demo.controller;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.SessionKeyConst;
import com.example.demo.constant.UrlConst;
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
import com.example.demo.constant.ViewNameConst;
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
            redirectAttributes.addFlashAttribute("message", AppUtil.getMessage(messageSource, MessageConst.SIGNUP_CONFIRM_INVALID_OPERATION));
            redirectAttributes.addFlashAttribute("isError", true);
            return AppUtil.doRedirect(UrlConst.SIGNUP_CONFIRM);
        }
        var signupConfirmStatus = service.chkTentativeSignupUser(loginId, oneTimeCode);
        return AppUtil.doRedirect(UrlConst.SIGNUP_COMPLETION);
    }

}
