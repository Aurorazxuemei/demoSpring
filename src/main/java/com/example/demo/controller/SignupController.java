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

/**
 * ユーザー登録画面Controllerクラス
 */
@Controller
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;
    private final MessageSource messageSource;

    /**
     * 画面の初期表示を行います。
     * @param model　モデル
     * @param signupForm　入力情報
     * @return　ユーザー登録画面
     */
    @GetMapping(UrlConst.SIGNUP)
    public String View(Model model, SignupForm signupForm) {
        model.addAttribute("signupForm", signupForm);
        return "signup";
    }

    /**
     * 画面の入力情報からユーザー登録処理を呼び出します。
     *
     * <p>ただし、入力チェックでエラーになった場合や登録済みのログインIDを使っていた場合は<br>
     * エラーメッセージを画面に表示します。
     * @param model　モデル
     * @param signupForm　フォームの入力情報
     * @param bindingResult　入力内容の単項目チェック結果
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

    /**
     * メッセージIDを使ってプロパティファイルからメッセージを取得し、画面に表示します。
     *
     * <p>また、画面でメッセージを表示する際に通常メッセージとエラーメッセージとで色分けをするため、<br>
     * その判定に必要な情報も画面に渡します。
     * @param model　モデル
     * @param messageId　プロパティファイルから取得したいメッセージのID
     * @param isError　エラー有無
     */
    private void editGuideMessage(Model model,String messageId,Boolean isError) {
        String message = AppUtil.getMessage(messageSource,messageId);
        model.addAttribute("message", message);
        model.addAttribute("isError", isError);
    }

    /**
     * ユーザ情報登録の結果メッセージキーを判断します。
     * @param userInfo　ユーザ登録結果(登録済みだった場合はEmpty)
     * @return　プロパティファイルから取得するメッセージの情報
     */
    private SinupMessage judgeMessageKey(Optional<UserInfo> userInfo) {
        if (userInfo.isEmpty()) {
            return SinupMessage.EXISTED_LOGIN_ID;
        } else {
            return SinupMessage.SUSSEED;
        }
    }

}
