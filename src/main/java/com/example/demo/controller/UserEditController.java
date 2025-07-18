package com.example.demo.controller;

import com.example.demo.constant.SessionKeyConst;
import com.example.demo.form.UserEditForm;
import com.example.demo.service.UserEditService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserEditController {
    private final UserEditService service;
    private final HttpSession session;

    @GetMapping("/userEdit")
    public String view(Model model, UserEditForm form) throws Exception {
        var loginId = (String) session.getAttribute(SessionKeyConst.SELECETED_LOGIN_ID);
        var userInfoOpt = service.searchUserInfo(loginId);
        if (userInfoOpt.isEmpty()) {
            throw new Exception("ログインIDに該当するユーザー情報が見つかりません。");
        }
        return"userEdit";
    }

}
