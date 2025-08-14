package com.example.demo.controller;

import com.example.demo.constant.UrlConst;
import com.example.demo.util.AppUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.constant.ViewNameConst;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class SignupConfirmController {

    @GetMapping(UrlConst.SIGNUP_CONFIRM)
    public String view() {
        return ViewNameConst.SIGNUP_CONFIRM;
    }
}
