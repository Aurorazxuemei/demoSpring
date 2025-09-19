package com.example.demo;

import com.example.demo.constant.*;
import com.example.demo.controller.ItemEditController;
import com.example.demo.dto.ItemEditResult;
import com.example.demo.dto.ItemUpdateInfo;
import com.example.demo.entity.ItemInfo;
import com.example.demo.form.ItemEditForm;
import com.example.demo.service.ItemEditService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ItemEditControllerTest {
    @InjectMocks
    private ItemEditController controller;
    @Mock
    private ItemEditService service;
    @Mock
    private HttpSession session;
    @Mock
    private MessageSource messageSource;
    @Mock
    private Mapper mapper;

    private final Integer itemId = 1;
    private ItemInfo dummyItem;
    private ItemEditForm dummyForm;
    private ItemUpdateInfo dummyUpdateInfo;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        dummyItem = new ItemInfo();
        dummyItem.setItemId(itemId);
        dummyItem.setItemName("Test Item");

        dummyForm = new ItemEditForm();
        dummyForm.setItemName("Updated Name");

        dummyUpdateInfo = new ItemUpdateInfo();
        dummyUpdateInfo.setItemId(itemId);
        dummyUpdateInfo.setItemName("Updated Name");
    }

    @Test
    void testViewSuccess()throws Exception{
        Model model = new ConcurrentModel();
        
        when(session.getAttribute(SessionKeyConst.SELECETED_ITEM_ID)).thenReturn(itemId);
        when(service.serchItemInfo(itemId)).thenReturn(Optional.of(dummyItem));
        when(mapper.map(dummyItem,ItemEditForm.class)).thenReturn(dummyForm);

        String viewName = controller.view(model,dummyForm);

        assertThat(viewName).isEqualTo(ViewNameConst.ITEM_EDIT_ERROR);
        assertThat(model.getAttribute( "itemEditForm")).isEqualTo(dummyForm);
    }
    @Test
    void testViewItemNotFound() throws Exception {
        Model model = new ConcurrentModel();

        when(session.getAttribute(SessionKeyConst.SELECETED_ITEM_ID)).thenReturn(itemId);
        when(service.serchItemInfo(itemId)).thenReturn(Optional.empty());
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Not found");

        String viewName = controller.view(model, new ItemEditForm());

        assertThat(viewName).isEqualTo(ViewNameConst.ITEM_EDIT_ERROR);
        assertThat(model.getAttribute(ModelKey.MESSAGE)).isEqualTo("Not found");
    }
    @Test
    void testUpdateItemSuccess() {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        ItemEditResult result = new ItemEditResult();
        result.setUpdateMessage(ItemEditMessage.SUCCEED);

        when(mapper.map(any(ItemEditForm.class), eq(ItemUpdateInfo.class))).thenReturn(dummyUpdateInfo);
        when(service.updateItemInfo(any(ItemUpdateInfo.class))).thenReturn(result);
        when(messageSource.getMessage(any(), any(), any())).thenReturn("更新成功");

        String viewName = controller.updateItem(dummyForm, redirectAttributes);

        assertThat(viewName).isEqualTo(AppUtil.doRedirect(UrlConst.ITEM_EDIT));
        assertThat(redirectAttributes.getFlashAttributes().get(ModelKey.IS_ERROR)).isEqualTo(false);
        assertThat(redirectAttributes.getFlashAttributes().get(ModelKey.MESSAGE)).isEqualTo("更新成功");
    }
    @Test
    void testUpdateItemFailed() {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        ItemEditResult result = new ItemEditResult();
        result.setUpdateMessage(ItemEditMessage.FAILED);

        when(mapper.map(any(ItemEditForm.class), eq(ItemUpdateInfo.class))).thenReturn(dummyUpdateInfo);
        when(service.updateItemInfo(any(ItemUpdateInfo.class))).thenReturn(result);
        when(messageSource.getMessage(any(), any(), any())).thenReturn("更新失败");

        String viewName = controller.updateItem(dummyForm, redirectAttributes);

        assertThat(viewName).isEqualTo(AppUtil.doRedirect(UrlConst.ITEM_EDIT));
        assertThat(redirectAttributes.getFlashAttributes().get(ModelKey.MESSAGE)).isEqualTo("更新失败");
        assertThat(redirectAttributes.getAttribute("err")).isNotNull();
    }
}
