package com.example.demo.controller;


import com.example.demo.constant.*;
import com.example.demo.dto.UserSerchInfo;
import com.example.demo.form.UserListForm;
import com.example.demo.service.UserListService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserListController {
    private final UserListService service;
    private final Mapper mapper;
    private final MessageSource messageSource;
    private final HttpSession session;

    @GetMapping(UrlConst.USER_LIST)
    public String View(Model model, UserListForm userListForm) {
        session.removeAttribute(SessionKeyConst.SELECETED_LOGIN_ID);
        var userInfos = service.editUserList();
        model.addAttribute("userListForm", userListForm); // ★これが必要！
        model.addAttribute("userList", userInfos);
        model.addAttribute("userStatusKinds", UserStatusKind.values());
        model.addAttribute("authorityKinds", AuthorityKind.values());
        return ViewNameConst.USER_LIST;
    }
    /**
     * 検索条件に合致するユーザー情報を画面に表示します。
     *
     * @param model モデル
     * @return 表示画面
     */
    @PostMapping( value = UrlConst.USER_LIST,params = "search")
    public String searchUser(Model model,UserListForm form){
        var searchDto = mapper.map(form, UserSerchInfo.class);
        var userInfos = service.editUserListByParam(searchDto);
        //model.addAttribute("userListForm", userListForm); // ★これが必要！
        model.addAttribute("userList", userInfos);
        model.addAttribute("userStatusKinds", UserStatusKind.values());
        model.addAttribute("authorityKinds", AuthorityKind.values());
        return ViewNameConst.USER_LIST;
    }

    /**
     * 編集機能の実装
     */
    @PostMapping(value = UrlConst.USER_LIST,params = "edit")
    public String updateUser(UserListForm form){
        session.setAttribute(SessionKeyConst.SELECETED_LOGIN_ID,form.getSelectedLoginId());
       // return "redirect:/userEdit";
        return AppUtil.doRedirect(UrlConst.USER_EDIT);
    }



    /**
     * 選択された行を削除する。
     *
     * @param redirectAttributes リダイレクト用オブジェクト
     * @return 表示画面
     */
    @PostMapping( value = UrlConst.USER_LIST,params = "delete")
    public String deleteUer(UserListForm form,  RedirectAttributes redirectAttributes){
        var deletedLoginedId = form.getSelectedLoginId();
        var executeResult = service.deleteUserInfoById(deletedLoginedId);
        redirectAttributes.addFlashAttribute(ModelKey.IS_ERROR, executeResult == UserDeleteResult.ERROR);
        redirectAttributes.addFlashAttribute(ModelKey.MESSAGE, AppUtil.getMessage(messageSource,executeResult.getMessageId()));
        //削除後、Formの選択されたログインID不要の為に、クリアします。
        redirectAttributes.addFlashAttribute("userListForm",form.clearSelectedLoginId());
        redirectAttributes.addFlashAttribute("operationKind", OperationKind.DELETE);
        return AppUtil.doRedirect(UrlConst.USER_LIST);
    }
}

