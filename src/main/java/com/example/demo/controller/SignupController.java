package com.example.demo.controller;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.SinupMessage;
import com.example.demo.constant.UrlConst;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.LoginForm;
import com.example.demo.form.SignupForm;
import com.example.demo.service.LoginService;
import com.example.demo.service.SignupService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;
    private final MessageSource messageSource;

    @GetMapping(UrlConst.SIGNUP)
    public String View(Model model, SignupForm signupForm) {
        model.addAttribute("signupForm", signupForm);
        return "signup";
    }

    /**
     * ユーザー登録
     * @param model　モデル
     * @param signupForm　フォームの入力情報
     * @param bindingResult　入力チェック結果
     */
    @PostMapping(UrlConst.SIGNUP)
    public void login(Model model, @Validated SignupForm signupForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
//            String message = AppUtil.getMessage(messageSource,MessageConst.FORM_ERROR);
//            model.addAttribute("message", message);
//            model.addAttribute("isError",true);
            editGuideMessage(model,MessageConst.FORM_ERROR,true);
            return;
        }
        var userInfo = signupService.registerUserInfo(signupForm);
        var signupMessage =judgeMessageKey(userInfo);
//        String message = AppUtil.getMessage(messageSource, signupMessage.getMessageId());
//        model.addAttribute("message", message);
//        model.addAttribute("isError", signupMessage.isError());
        editGuideMessage(model,signupMessage.getMessageId(),signupMessage.isError());
    }

    private void editGuideMessage(Model model,String messageId,Boolean isError) {
        String message = AppUtil.getMessage(messageSource,messageId);
        model.addAttribute("message", message);
        model.addAttribute("isError", isError);
    }

    private SinupMessage judgeMessageKey(Optional<UserInfo> userInfo) {
        if (userInfo.isEmpty()) {
            return SinupMessage.EXISTED_LOGIN_ID;
        } else {
            return SinupMessage.SUSSEED;
        }
    }

}
