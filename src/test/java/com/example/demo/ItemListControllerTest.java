package com.example.demo;

import com.example.demo.constant.*;
import com.example.demo.controller.ItemListController;
import com.example.demo.dto.ItemSearchInfo;
import com.example.demo.dto.StaffInfo;
import com.example.demo.entity.ItemInfo;
import com.example.demo.form.ItemListForm;
import com.example.demo.service.ItemListService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * ItemListController の単体テスト
 */
@ExtendWith(MockitoExtension.class)
class ItemListControllerTest {

    @Mock
    private ItemListService service;

    @Mock
    private Mapper mapper;

    @Mock
    private HttpSession session;

    @Mock
    private MessageSource messageSource;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ItemListController controller; // ← テスト対象のコントローラ

    private ItemListForm form;

    @BeforeEach
    void setUp() {
        form = new ItemListForm();
        form.setSelectedItemId(1);
    }

    /**
     * 初期表示のテスト
     */
    @Test
    void testView() {
        //準備
        StaffInfo staffInfo1 = new StaffInfo();
        staffInfo1.setUserId("1");
        staffInfo1.setUserName("name1");
        StaffInfo staffInfo2 = new StaffInfo();
        staffInfo1.setUserId("2");
        staffInfo1.setUserName("name2");

        when(service.obtainUserIdList()).thenReturn(Arrays.asList(staffInfo1,staffInfo2));
        //実行
        var model = new org.springframework.ui.ConcurrentModel();
        var result = controller.view(model, form);

        assertThat(result).isEqualTo(ViewNameConst.ITEM_LIST);
        assertThat(model.getAttribute("itemListForm")).isEqualTo(form);
        assertThat(model.getAttribute("userIdOptions")).isEqualTo(List.of(staffInfo1, staffInfo2));

        verify(service, times(1)).obtainUserIdList();
    }

    /**
     * 検索ボタンのテスト
     */
    @Test
    void testSearchItem() {
        ItemInfo itemInfo1 = new ItemInfo();
        itemInfo1.setItemName("item1");
        itemInfo1.setItemId(1);
        var info = new ItemSearchInfo();
        when(mapper.map(any(ItemListForm.class), eq(ItemSearchInfo.class))).thenReturn(info);
        when(service.editItemListByParam(info)).thenReturn(Arrays.asList(itemInfo1));

        String result = controller.searchItem(form, redirectAttributes);

        verify(mapper).map(any(ItemListForm.class), eq(ItemSearchInfo.class));
        verify(service).editItemListByParam(info);
        verify(redirectAttributes, atLeastOnce()).addFlashAttribute(anyString(), any());
        assertThat(result).isEqualTo(AppUtil.doRedirect(UrlConst.ITEM_LIST));
    }

    /**
     * 編集ボタンのテスト
     */
    @Test
    void testUpdateItem() {
        String result = controller.updateItem(form);

        verify(session).setAttribute(SessionKeyConst.SELECETED_ITEM_ID, 1);
        assertThat(result).isEqualTo(AppUtil.doRedirect(UrlConst.ITEM_EDIT));
    }

    /**
     * 削除ボタンのテスト
     */
    @Test
    void testDeleteItem() {
        when(service.deleteItemInfoById(1)).thenReturn(ItemDeleteResult.SUCCESS);
        when(messageSource.getMessage(anyString(), any(), any(Locale.class))).thenReturn("削除成功");

        String result = controller.deleteItem(form, redirectAttributes);

        verify(service).deleteItemInfoById(1);
        verify(messageSource).getMessage(anyString(), any(), any(Locale.class));
        verify(redirectAttributes, atLeastOnce()).addFlashAttribute(anyString(), any());
        assertThat(result).isEqualTo(AppUtil.doRedirect(UrlConst.ITEM_LIST));
    }
}
