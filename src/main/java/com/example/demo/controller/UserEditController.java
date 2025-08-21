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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserEditController {
    private final UserEditService service;
    private final HttpSession session;
    private final Mapper mapper;
    private final MessageSource messageSource;
    /** リダイレクトパラメータ：エラー有 */
    private static final String REDIRECT_PRAM_ERR = "err";

    @GetMapping(UrlConst.USER_EDIT)
    public String view(Model model, UserEditForm form) throws Exception {
        var loginId = (String) session.getAttribute(SessionKeyConst.SELECETED_LOGIN_ID);
        var userInfoOpt = service.searchUserInfo(loginId);
        if (userInfoOpt.isEmpty()) {
            model.addAttribute(ModelKey.MESSAGE,AppUtil.getMessage(messageSource,MessageConst.USEREDIT_NON_EXISTED_LOGIN_ID));
            return ViewNameConst.USER_EDIT_ERROR;
        }
       var userInfo = userInfoOpt.get();
        model.addAttribute("userEditForm", mapper.map(userInfo, UserEditForm.class));
        model.addAttribute("userEditInfo", mapper.map(userInfo, UserEditInfo.class));
        model.addAttribute("userStatusKindOptions", UserStatusKind.values());
        model.addAttribute("authorityKindOptions", AuthorityKind.values());
        return ViewNameConst.USER_EDIT;
    }
    /**
     * 画面の更新エラー時にエラーメッセージを表示します。
     *
     * @param model モデル
     * @return ユーザー編集エラー画面テンプレート名
     */
    @GetMapping(value = UrlConst.USER_EDIT, params = REDIRECT_PRAM_ERR)
    public String viewWithError(Model model) {
        return ViewNameConst.USER_EDIT_ERROR;
    }

    @PostMapping(value=UrlConst.USER_EDIT,params = "update")
    public String updateUser(UserEditForm form, RedirectAttributes redirectAttributes){
    var updateDto = mapper.map(form, UserUpdateInfo.class);
    updateDto.setLoginId((String)session.getAttribute(SessionKeyConst.SELECETED_LOGIN_ID));
    UserEditResult updateResult = service.updateUserInfo(updateDto);
    var updateMessage = updateResult.getUpdateMessage();
    if(updateMessage == UserEditMessage.FAILED){
        redirectAttributes.addFlashAttribute(ModelKey.MESSAGE,AppUtil.getMessage(messageSource,updateMessage.getMessageId()));
        // リダイレクト時に URL パラメータ (?err) を付与して、エラーハンドラ(@GetMapping(..., params="err"))を呼び出すため
        redirectAttributes.addAttribute(REDIRECT_PRAM_ERR, "");
        //return ViewNameConst.USER_EDIT_ERROR;
        return AppUtil.doRedirect(UrlConst.USER_EDIT);
    }
    //setupCommonInfo(model,updateResult.getUpdateUserInfo());
    redirectAttributes.addFlashAttribute(ModelKey.IS_ERROR,false);
    redirectAttributes.addFlashAttribute(ModelKey.MESSAGE, AppUtil.getMessage(messageSource,updateMessage.getMessageId()));
    return AppUtil.doRedirect(UrlConst.USER_EDIT);
    }


}
