package com.example.demo.controller;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.ModelKey;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewNameConst;
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

/**
 * ログイン画面Controller
 *
 * @author 張雪梅
 */
@Controller
@RequiredArgsConstructor
public class LoginController {


    private final PasswordEncoder passwordEncoder;
    private final LoginService loginService;
    private final MessageSource messageSource;
    private final HttpSession session;


//    private static final String LOGIN_ID = "user";
//    private static final String PASSWORD = "pwd";

    /**
     * 画面の初期表示を行います。
     * @param model　モデル
     * @param loginForm　入力情報
     * @return ログイン画面テンプレート名
     */
    @GetMapping(UrlConst.LOGIN)
    public String View(Model model, LoginForm loginForm) {
        model.addAttribute("loginForm", loginForm);
        return ViewNameConst.LOGIN;
    }
    /**
     * ログインエラー時にセッションからエラーメッセージを取得して、画面の表示を行います。
     * @param model　モデル
     * @param loginForm　入力情報
     * @return ログイン画面テンプレート名
     */
    @GetMapping(value = UrlConst.LOGIN, params = "error")
    public String viewWithError(Model model, LoginForm loginForm) {
        var errorInfo = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        model.addAttribute(ModelKey.MESSAGE, errorInfo.getMessage());
        model.addAttribute(ModelKey.IS_ERROR, true);
        return ViewNameConst.LOGIN;
    }
}
