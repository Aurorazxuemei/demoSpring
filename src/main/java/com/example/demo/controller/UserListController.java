package com.example.demo.controller;


import com.example.demo.form.UserListForm;
import com.example.demo.service.UserListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;

@Controller
@RequiredArgsConstructor
public class UserListController {
    private final UserListService service;

    @GetMapping("/userList")
    public String View(Model model, UserListForm userListForm) {
         var userInfos = service.editUserList();
        model.addAttribute("userList", userInfos);
        model.addAttribute("userStatusKinds", UserStatusKind.values());
        model.addAttribute("authorityKinds", AuthorityKind.values());
        return "userList";
    }
}

