package com.example.demo.controller;

import com.example.demo.form.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String View(Model model, LoginForm loginForm){

        return "login";
    }
@PostMapping("/login")
    public void login(LoginForm loginForm){
        System.out.println(loginForm.toString());
}


}
