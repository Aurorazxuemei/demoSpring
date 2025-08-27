package com.example.demo.controller;

import com.example.demo.constant.*;
import com.example.demo.dto.ItemSearchInfo;
import com.example.demo.dto.StaffInfo;
import com.example.demo.dto.UserSearchInfo;
import com.example.demo.form.ItemListForm;
import com.example.demo.form.UserListForm;
import com.example.demo.service.ItemListService;
import com.example.demo.service.UserListService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.eclipse.angus.mail.imap.protocol.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ItemListController {
    private static final String KEY_ITEM_LIST_FORM = "itemListForm";
    private static final String KEY_USER_ID_OPTIONS = "userIdOptions";
    private final ItemListService service;
    private final Mapper mapper;
    private static final String KEY_ITEM_LIST = "itemList";
    private static final String KEY_OPERATION_KIND =  "operationKind";

    /**
     * 画面の初期表示を行います。
     *
     * <p>またその際、画面選択項目「アカウント状態」「所有権限」の選択肢を生成して画面に渡します。
     *
     * @param model モデル
     * @param form 入力情報
     * @return 商品一覧画面テンプレート名
     */
    @GetMapping(UrlConst.ITEM_LIST)
    public String view(Model model, ItemListForm form) {
        model.addAttribute(KEY_ITEM_LIST_FORM, form);
       var staffInfos =  service.obtainUserIdList();
        model.addAttribute(KEY_USER_ID_OPTIONS, staffInfos);
        return ViewNameConst.ITEM_LIST;
    }

    /**
     * 検索条件に合致する商品情報を画面に表示します。
     *
     * @param  form 商品一覧画面Formクラス
     * @return 表示画面
     */
    @PostMapping( value = UrlConst.ITEM_LIST,params = "search")
    public String searchItem(ItemListForm form, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(KEY_ITEM_LIST_FORM, form);
        var searchDto = mapper.map(form, ItemSearchInfo.class);
        var itemInfos = service.editItemListByParam(searchDto);
        redirectAttributes.addFlashAttribute(KEY_ITEM_LIST, itemInfos);
        redirectAttributes.addFlashAttribute(KEY_OPERATION_KIND, ItemListController.OperationKind.SEARCH);
        return AppUtil.doRedirect(UrlConst.ITEM_LIST);
    }
    /**
     * 商品一覧画面で行われる操作種別を表す列挙型です。
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
