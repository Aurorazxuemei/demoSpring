package com.example.demo.controller;

import com.example.demo.constant.*;
import com.example.demo.form.ItemEditForm;
import com.example.demo.service.ItemEditService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
public class ItemEditController {
    private static final String KEY_ITEM_EDIT_FORM = "itemEditForm";
    private final HttpSession session;
    private final ItemEditService service;
    private final MessageSource messageSource;
    private final Mapper mapper;

    @GetMapping(UrlConst.ITEM_EDIT)
    public String view(Model model, ItemEditForm form) throws Exception {
        var itemId = (String)session.getAttribute(SessionKeyConst.SELECETED_ITEM_ID);
        var itemInfoOpt = service.serchItemInfo(itemId);
        if (itemInfoOpt.isEmpty()) {
            model.addAttribute(ModelKey.MESSAGE, AppUtil.getMessage(messageSource, MessageConst.USEREDIT_NON_EXISTED_ITEM_ID));
            return ViewNameConst.ITEM_EDIT_ERROR;
        }
        var itemInfo = itemInfoOpt.get();
        model.addAttribute(KEY_ITEM_EDIT_FORM, mapper.map(itemInfo, ItemEditForm.class));
        return ViewNameConst.ITEM_EDIT;
    }
}
