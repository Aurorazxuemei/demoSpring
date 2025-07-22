package com.example.demo.controller;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.SessionKeyConst;
import com.example.demo.constant.UserEditMessage;
import com.example.demo.constant.UserStatusKind;
import com.example.demo.dto.UserEditInfo;
import com.example.demo.dto.UserUpdateInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserEditForm;
import com.example.demo.service.UserEditService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.dto.UserEditResult;

@Controller
@RequiredArgsConstructor
public class UserEditController {
    private final UserEditService service;
    private final HttpSession session;
    private final Mapper mapper;
    private final MessageSource messageSource;

    @GetMapping("/userEdit")
    public String view(Model model, UserEditForm form) throws Exception {
        var loginId = (String) session.getAttribute(SessionKeyConst.SELECETED_LOGIN_ID);
        var userInfoOpt = service.searchUserInfo(loginId);
        if (userInfoOpt.isEmpty()) {
            throw new Exception("ログインIDに該当するユーザー情報が見つかりません。");
        }
        setupCommonInfo(model,userInfoOpt.get());
        return"userEdit";
    }

    private void setupCommonInfo(Model model, UserInfo userInfo) {
        model.addAttribute("userEditForm", mapper.map(userInfo, UserEditForm.class));
        model.addAttribute("userEditInfo", mapper.map(userInfo, UserEditInfo.class));
        model.addAttribute("userStatusKindOptions", UserStatusKind.values());
        model.addAttribute("authorityKindOptions", AuthorityKind.values());
    }

    @PostMapping(value="/userEdit",params = "update")
    public String updateUser(Model model,UserEditForm form){
    var updateDto = mapper.map(form, UserUpdateInfo.class);
    updateDto.setLoginId((String)session.getAttribute(SessionKeyConst.SELECETED_LOGIN_ID));
    UserEditResult updateResult = service.updateUserInfo(updateDto);
    setupCommonInfo(model,updateResult.getUpdateUserInfo());
    var updateMessage = updateResult.getUpdateMessage();
    model.addAttribute("isError",updateMessage == UserEditMessage.FAILED);
    model.addAttribute("message", AppUtil.getMessage(messageSource,updateMessage.getMessageId()));
    return "userEdit";
    }


}
