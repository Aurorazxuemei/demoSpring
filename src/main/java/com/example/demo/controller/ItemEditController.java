package com.example.demo.controller;

import com.example.demo.constant.*;
import com.example.demo.dto.ItemEditResult;
import com.example.demo.dto.ItemUpdateInfo;
import com.example.demo.form.ItemEditForm;
import com.example.demo.service.ItemEditService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ItemEditController {
    private static final String KEY_ITEM_EDIT_FORM = "itemEditForm";
    private final HttpSession session;
    private final ItemEditService service;
    private final MessageSource messageSource;
    private final Mapper mapper;
    /** リダイレクトパラメータ：エラー有 */
    private static final String REDIRECT_PRAM_ERR = "err";

    @GetMapping(UrlConst.ITEM_EDIT)
    public String view(Model model, ItemEditForm form) throws Exception {
        var itemId = (Integer)session.getAttribute(SessionKeyConst.SELECETED_ITEM_ID);
        var itemInfoOpt = service.serchItemInfo(itemId);
        if (itemInfoOpt.isEmpty()) {
            model.addAttribute(ModelKey.MESSAGE, AppUtil.getMessage(messageSource, MessageConst.USEREDIT_NON_EXISTED_ITEM_ID));
            return ViewNameConst.ITEM_EDIT_ERROR;
        }
        var itemInfo = itemInfoOpt.get();
        model.addAttribute(KEY_ITEM_EDIT_FORM, mapper.map(itemInfo, ItemEditForm.class));
        return ViewNameConst.ITEM_EDIT;
    }
    /**
     * 画面の更新エラー時にエラーメッセージを表示します。
     *
     * @param model モデル
     * @return 商品編集エラー画面テンプレート名
     */
    @GetMapping(value = UrlConst.ITEM_EDIT, params = REDIRECT_PRAM_ERR)
    public String viewWithError(Model model) {
        return ViewNameConst.ITEM_EDIT_ERROR;
    }
    @PostMapping(value =UrlConst.ITEM_EDIT,params = "update")
    public String updateItem(ItemEditForm form, RedirectAttributes redirectAttributes) {
        var updateDto = mapper.map(form, ItemUpdateInfo.class);
        ItemEditResult updateResult = service.updateItemInfo(updateDto);
        var updateMessage = updateResult.getUpdateMessage();
        if(updateMessage == ItemEditMessage.FAILED){
            redirectAttributes.addFlashAttribute(ModelKey.MESSAGE,AppUtil.getMessage(messageSource,updateMessage.getMessageId()));
            // リダイレクト時に URL パラメータ (?err) を付与して、エラーハンドラ(@GetMapping(..., params="err"))を呼び出すため
            redirectAttributes.addAttribute(REDIRECT_PRAM_ERR, "");
            return AppUtil.doRedirect(UrlConst.ITEM_EDIT);
        }
        redirectAttributes.addFlashAttribute(ModelKey.IS_ERROR,false);
        redirectAttributes.addFlashAttribute(ModelKey.MESSAGE,AppUtil.getMessage(messageSource,updateMessage.getMessageId()));
        return AppUtil.doRedirect(UrlConst.ITEM_EDIT);
    }

}
