package com.example.demo.controller;

import com.example.demo.constant.SessionKeyConst;
import com.example.demo.constant.UrlConst;
import com.example.demo.constant.ViewNameConst;
import com.example.demo.form.ItemEditForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiredArgsConstructor
public class ItemEditController {
    private static final String KEY_ITEM_EDIT_FORM = "itemEditForm";
    private final HttpSession session;
    @GetMapping(UrlConst.ITEM_EDIT)
    public String view(Model model, ItemEditForm form) throws Exception {
        var itemId = (String)session.getAttribute(SessionKeyConst.SELECETED_ITEM_ID);
        model.addAttribute(KEY_ITEM_EDIT_FORM, form);
        return ViewNameConst.ITEM_EDIT;
    }
}
