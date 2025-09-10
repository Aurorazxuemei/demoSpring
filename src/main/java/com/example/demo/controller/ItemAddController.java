package com.example.demo.controller;

import com.example.demo.constant.*;
import com.example.demo.dto.ItemAddInfo;
import com.example.demo.dto.ItemEditResult;
import com.example.demo.dto.ItemSearchInfo;
import com.example.demo.dto.ItemUpdateInfo;
import com.example.demo.entity.ItemInfo;
import com.example.demo.form.ItemAddForm;
import com.example.demo.form.ItemEditForm;
import com.example.demo.form.SignupForm;
import com.example.demo.service.ItemAddService;
import com.example.demo.service.ItemEditService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ItemAddController {
    private static final String KEY_ITEM_ADD_FORM = "itemAddForm";
    private final HttpSession session;
    private final ItemAddService service;
    private final MessageSource messageSource;
    private final Mapper mapper;
    /**画面で使用するフォームクラス名*/
    private static final String FORM_CLASS_NAME="itemAddForm";
    /**
     * リダイレクトパラメータ：エラー有
     */
    private static final String REDIRECT_PRAM_ERR = "err";

    @GetMapping(UrlConst.ITEM_ADD)
    public String view(Model model, ItemAddForm form) throws Exception {
        model.addAttribute(KEY_ITEM_ADD_FORM, form);
        return ViewNameConst.ITEM_ADD;
    }

    @PostMapping(value=UrlConst.ITEM_EDIT,params = "add")
    public String view(@Validated ItemAddForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute("itemAddForm", form);
        if(bindingResult.hasErrors()) {
            editGuideMessage(form,bindingResult,MessageConst.FORM_ERROR,redirectAttributes);
            return ViewNameConst.ITEM_ADD;
        }
        var itemInfoDto = mapper.map(form, ItemAddInfo.class);
        var addResult = service.addItem(itemInfoDto);

        return AppUtil.doRedirect(UrlConst.ITEM_ADD);
    }

    /**
     * メッセージIDを使ってプロパティファイルからメッセージを取得し、画面に表示します。
     *
     * <p>また、画面でメッセージを表示する際に通常メッセージとエラーメッセージとで色分けをするため、<br>
     * その判定に必要な情報も画面に渡します。
     * @param form 入力情報
     * @param bdResult 入力内容の単項目チェック結果
     * @param messageId　プロパティファイルから取得したいメッセージのID
     * @param redirectAttributes リダイレクト用モデル
     */
    private void editGuideMessage(ItemAddForm form, BindingResult bdResult, String messageId,
                                  RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(ModelKey.MESSAGE, AppUtil.getMessage(messageSource, messageId));
        redirectAttributes.addFlashAttribute(ModelKey.IS_ERROR, true);
        //入力されたフォーム (SignupForm) をリダイレクト後の画面に渡す
        //これにより再描画時に入力値を保持できる
        redirectAttributes.addFlashAttribute(form);
        //BindingResult（バリデーション結果）をリダイレクト先にも渡す
        //BindingResult.MODEL_KEY_PREFIX + フォーム名 という形式にしないと、Springが自動的にエラー情報を関連付けられないため、この書き方になっています
        redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + FORM_CLASS_NAME, bdResult);

    }
}