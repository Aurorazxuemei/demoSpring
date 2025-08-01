package com.example.demo.controller;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;
import com.example.demo.util.AppUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@RequiredArgsConstructor
public class LoginController {


    private final PasswordEncoder passwordEncoder;
    private final LoginService loginService;
    private final MessageSource messageSource;
    private final HttpSession session;


//    private static final String LOGIN_ID = "user";
//    private static final String PASSWORD = "pwd";

    @GetMapping(UrlConst.LOGIN)
    public String View(Model model, LoginForm loginForm) {
        model.addAttribute("loginForm", loginForm);
        return "login";
    }
//    //ログインボタンを押したときに呼ばれる手動ログイン処理
//    //現在は Spring Security のログイン機能を使っているため、自前のログイン処理は不要になった
//    @PostMapping(UrlConst.LOGIN)
//    public String login(Model model, LoginForm loginForm) {
//        var userInfo = loginService.searchUserById(loginForm.getLoginId());
//
//        //var encodedpassword = passwordEncoder.encode(loginForm.getPassword());
//        var isCorrectUserAuth = userInfo.isPresent()
//                && passwordEncoder.matches(loginForm.getPassword(), userInfo.get().getPassword());
//        if (isCorrectUserAuth) {
//            return "redirect:/menu";
//        } else {
//            String errorMsg = AppUtil.getMessage(messageSource,MessageConst.LOGIN_WRONG_INPUT);
//            model.addAttribute("errorMsg", errorMsg);
//            return "login";
//        }
//    }

    //Spring Security のログイン失敗時に呼ばれる処理
    @GetMapping(value = UrlConst.LOGIN, params = "error")
    public String viewWithError(Model model, LoginForm loginForm) {
        var errorInfo = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//        String errorMsg = "ログインに失敗しました。";
//        if (errorInfo instanceof BadCredentialsException) {
//            errorMsg = "ユーザー名またはパスワードが正しくありません。";
//        } else if (errorInfo instanceof LockedException) {
//            errorMsg = "アカウントがロックされています。";
//        } else if (errorInfo instanceof DisabledException) {
//            errorMsg = "アカウントが無効になっています。";
//        } else if (errorInfo instanceof AccountExpiredException) {
//            errorMsg = "アカウントの有効期限が切れています。";
//        } else if (errorInfo instanceof CredentialsExpiredException) {
//            errorMsg = "パスワードの有効期限が切れています。";
//        }

        model.addAttribute("errorMsg", errorInfo.getMessage());
        return "login";
    }

}
