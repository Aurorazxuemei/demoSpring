package com.example.demo.controller;


import com.example.demo.form.UserListForm;
import com.example.demo.service.UserListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserListController {
    private final UserListService service;

    @GetMapping("/userList")
    public String View(Model model, UserListForm userListForm) {
         var userInfos = service.editUserList();
//        model.addAttribute("userListForm", userListForm); // ★これが必要！
        model.addAttribute("userList", userInfos);
        model.addAttribute("userStatusKinds", UserStatusKind.values());
        model.addAttribute("authorityKinds", AuthorityKind.values());
        return "userList";
    }
    /**
     * 検索条件に合致するユーザー情報を画面に表示します。
     *
     * @param model モデル
     * @return 表示画面
     */
    @PostMapping( "/userList")
    public String searchUser(Model model,UserListForm form){
        var userInfos = service.editUserListByParam(form);
        //model.addAttribute("userListForm", userListForm); // ★これが必要！
        model.addAttribute("userList", userInfos);
        model.addAttribute("userStatusKinds", UserStatusKind.values());
        model.addAttribute("authorityKinds", AuthorityKind.values());
        return "userList";
    }
}

