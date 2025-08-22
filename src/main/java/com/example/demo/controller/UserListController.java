package com.example.demo.controller;


import com.example.demo.constant.*;
import com.example.demo.dto.UserListInfo;
import com.example.demo.dto.UserSearchInfo;
import com.example.demo.form.UserListForm;
import com.example.demo.service.UserListService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserListController {
    private final UserListService service;
    private final Mapper mapper;
    private final MessageSource messageSource;
    private final HttpSession session;
    private static final String KEY_USER_STATUS_KIND_OPTIONS = "userStatusKindOptions";
    private static final String KEY_USER_LIST_FORM = "userListForm";
    private static final String KEY_USER_LIST = "userList";
    private static final String KEY_AUTHORITY_KIND_OPTIONS = "authorityKindOptions";
    private static final String KEY_OPERATION_KIND = "operationKind";

    /**
     * 画面の初期表示を行います。
     *
     * <p>またその際、画面選択項目「アカウント状態」「所有権限」の選択肢を生成して画面に渡します。
     *
     * @param model モデル
     * @param form 入力情報
     * @return ユーザー一覧画面テンプレート名
     */
    @GetMapping(UrlConst.USER_LIST)
    public String view(Model model, UserListForm form) {
        session.removeAttribute(SessionKeyConst.SELECETED_LOGIN_ID);
        model.addAttribute(KEY_USER_LIST_FORM, form); // ★これが必要！
        model.addAttribute(KEY_USER_LIST, editUserListInfo(model));
        model.addAttribute(KEY_USER_STATUS_KIND_OPTIONS, UserStatusKind.values());
        model.addAttribute(KEY_AUTHORITY_KIND_OPTIONS, AuthorityKind.values());
        return ViewNameConst.USER_LIST;
    }

    /**
     * 初期表示、検索後や削除後のリダイレクトによる再表示のいずれかかを判定して画面に表示する一覧情報を作成します。
     *
     * @param model モデル
     * @return ユーザー一覧情報
     */
    @SuppressWarnings("unchecked")
    private List<UserListInfo> editUserListInfo(Model model) {
        var doneSearchOrDelete = model.containsAttribute(KEY_OPERATION_KIND);
        if (doneSearchOrDelete) {
            var operationKind = (OperationKind) model.getAttribute(KEY_OPERATION_KIND);
            if (operationKind == OperationKind.SEARCH) {
                return (List<UserListInfo>) model.getAttribute(KEY_USER_LIST);
            }
            if (operationKind == OperationKind.DELETE) {
                var searchDto = mapper.map((UserListForm) model.getAttribute(KEY_USER_LIST_FORM), UserSearchInfo.class);
                return service.editUserListByParam(searchDto);
            }
        }

        return service.editUserList();
    }

    /**
     * 検索条件に合致するユーザー情報を画面に表示します。
     *
     * @param  form ユーザー登録画面Formクラス
     * @return 表示画面
     */
    @PostMapping( value = UrlConst.USER_LIST,params = "search")
    public String searchUser(UserListForm form,RedirectAttributes redirectAttributes) {
        var searchDto = mapper.map(form, UserSearchInfo.class);
        var userInfos = service.editUserListByParam(searchDto);
        redirectAttributes.addFlashAttribute(KEY_USER_LIST, userInfos);
        redirectAttributes.addFlashAttribute(KEY_USER_LIST_FORM, form);
        redirectAttributes.addFlashAttribute(KEY_OPERATION_KIND, OperationKind.SEARCH);
        return AppUtil.doRedirect(UrlConst.USER_LIST);
    }

    /**
     * 選択行のユーザー情報を削除して、最新情報で画面を再表示します。
     * @param form 入力情報
     * @return リダイレクトURL
     */
    @PostMapping(value = UrlConst.USER_LIST,params = "edit")
    public String updateUser(UserListForm form){
        session.setAttribute(SessionKeyConst.SELECETED_LOGIN_ID,form.getSelectedLoginId());
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
        redirectAttributes.addFlashAttribute(KEY_USER_LIST_FORM,form.clearSelectedLoginId());
        redirectAttributes.addFlashAttribute(KEY_OPERATION_KIND, OperationKind.DELETE);
        return AppUtil.doRedirect(UrlConst.USER_LIST);
    }
    /**
     * ユーザー一覧画面で行われる操作種別を表す列挙型です。
     *
     * <p>主に以下の用途で使用されます：
     * <ul>
     *   <li>{@link #SEARCH} - 検索ボタンが押された場合</li>
     *   <li>{@link #DELETE} - 削除ボタンが押された場合</li>
     * </ul>
     *
     * <p>コントローラ内で、リダイレクト後の画面再表示時に、
     * どの操作を実行したのかを判定するために利用します。
     */
    public enum OperationKind {
        SEARCH, DELETE
    }
}

