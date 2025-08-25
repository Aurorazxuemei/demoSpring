package com.example.demo.controller;

import com.example.demo.constant.*;
import com.example.demo.dto.StaffInfo;
import com.example.demo.form.ItemListForm;
import com.example.demo.form.UserListForm;
import com.example.demo.service.ItemListService;
import com.example.demo.service.UserListService;
import lombok.RequiredArgsConstructor;
import org.eclipse.angus.mail.imap.protocol.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
public class ItemListController {
    private static final String KEY_ITEM_LIST_FORM = "itemListForm";
    private static final String KEY_USER_ID_OPTIONS = "userIdOptions";
    private final ItemListService service;
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
        return ViewNameConst.ITEM;
    }

}
