package com.example.demo.controller;

import com.example.demo.form.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private static final String LOGIN_ID = "user";
    private static final String PASSWORD = "pwd";

    @GetMapping("/login")
    public String View(Model model, LoginForm loginForm) {
        model.addAttribute("loginForm", loginForm);
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model,LoginForm loginForm) {
        var isCorrectUserAuth = loginForm.getLoginId().equals(LOGIN_ID)
                && loginForm.getPassword().equals(PASSWORD);
        if (isCorrectUserAuth) {
            return "redirect:/menu";
        }else{
            model.addAttribute("errorMsg", "ログインIDとパスワードの組み合わせが間違っています");
            return "login";
        }
    }


}
