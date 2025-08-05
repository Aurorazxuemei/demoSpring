package com.example.demo.controller;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UrlConst;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    @GetMapping(UrlConst.MENU)
    public String view(@AuthenticationPrincipal User user, Model model) {
        var hasUserManageAuth = user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority()
                        .equals(AuthorityKind.ITEM_AND_USER_MANAGER.getCode()));
        model.addAttribute("hasUserManageAuth", hasUserManageAuth);
        return "menu";
    }
}
