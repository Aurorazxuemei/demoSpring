package com.example.demo.controller;

import com.example.demo.form.LoginForm;
import com.example.demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class LoginController {


    private final PasswordEncoder passwordEncoder;
    private final LoginService loginService;

//    private static final String LOGIN_ID = "user";
//    private static final String PASSWORD = "pwd";

    @GetMapping("/login")
    public String View(Model model, LoginForm loginForm) {
        model.addAttribute("loginForm", loginForm);
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, LoginForm loginForm) {
        var userInfo = loginService.searchUserById(loginForm.getLoginId());

        //var encodedpassword = passwordEncoder.encode(loginForm.getPassword());
        var isCorrectUserAuth = userInfo.isPresent()
                && passwordEncoder.matches(loginForm.getPassword(), userInfo.get().getPassword());
        if (isCorrectUserAuth) {
            return "redirect:/menu";
        } else {
            model.addAttribute("errorMsg", "ログインIDとパスワードの組み合わせが間違っています");
            return "login";
        }
    }


}
