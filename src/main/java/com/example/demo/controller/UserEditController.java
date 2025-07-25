package com.example.demo.controller;

import com.example.demo.constant.*;
import com.example.demo.dto.UserEditInfo;
import com.example.demo.dto.UserUpdateInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserEditForm;
import com.example.demo.form.UserListForm;
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

    @GetMapping(UrlConst.USER_EDIT)
    public String view(Model model, UserEditForm form) throws Exception {
        var loginId = (String) session.getAttribute(SessionKeyConst.SELECETED_LOGIN_ID);
//        var userListForm = (UserListForm) session.getAttribute(SessionKeyConst.SELECETED_LOGIN_ID);
//        var loginId = userListForm.getLoginId();
        var userInfoOpt = service.searchUserInfo(loginId);
        if (userInfoOpt.isEmpty()) {
            model.addAttribute("message",AppUtil.getMessage(messageSource,MessageConst.USEREDIT_NON_EXISTED_LOGIN_ID));
            return ViewNameConst.USER_EDIT_ERROR;
        }
        setupCommonInfo(model,userInfoOpt.get());
        return ViewNameConst.USER_EDIT;
    }

    private void setupCommonInfo(Model model, UserInfo userInfo) {
        model.addAttribute("userEditForm", mapper.map(userInfo, UserEditForm.class));
        model.addAttribute("userEditInfo", mapper.map(userInfo, UserEditInfo.class));
        model.addAttribute("userStatusKindOptions", UserStatusKind.values());
        model.addAttribute("authorityKindOptions", AuthorityKind.values());
    }

    @PostMapping(value=UrlConst.USER_EDIT,params = "update")
    public String updateUser(Model model,UserEditForm form){
    var updateDto = mapper.map(form, UserUpdateInfo.class);
    updateDto.setLoginId((String)session.getAttribute(SessionKeyConst.SELECETED_LOGIN_ID));
    UserEditResult updateResult = service.updateUserInfo(updateDto);
    var updateMessage = updateResult.getUpdateMessage();
    if(updateMessage == UserEditMessage.FAILED){
        model.addAttribute("message",AppUtil.getMessage(messageSource,updateMessage.getMessageId()));
        return ViewNameConst.USER_EDIT_ERROR;
    }
    setupCommonInfo(model,updateResult.getUpdateUserInfo());
    model.addAttribute("isError",false);
    model.addAttribute("message", AppUtil.getMessage(messageSource,updateMessage.getMessageId()));
    return ViewNameConst.USER_EDIT;
    }


}
