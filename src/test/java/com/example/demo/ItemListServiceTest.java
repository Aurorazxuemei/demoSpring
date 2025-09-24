package com.example.demo;

import com.example.demo.constant.ItemDeleteResult;
import com.example.demo.dto.ItemSearchInfo;
import com.example.demo.dto.StaffInfo;
import com.example.demo.entity.ItemInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.ItemInfoRepository;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.ItemListServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ItemListServiceTest {
    @Mock
    private UserInfoRepository userInfoRepository;
    @Mock
    private ItemInfoRepository itemInfoRepository;
    @InjectMocks
    //private ItemListService itemListService;
    private ItemListServiceImpl itemListServiceImpl;
    public ItemListServiceTest() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testObtainUserIdList() {

        // 準備: UserInfo のテストデータを作成//given
        UserInfo user1 = new UserInfo();
        user1.setLoginId("id_1");
        user1.setUserName("name_1");
        UserInfo user2 = new UserInfo();
        user2.setLoginId("id_2");
        user2.setUserName("name_2");
//        List<UserInfo> userInfoList = new ArrayList<>();
//        userInfoList.add(user1);
//        userInfoList.add(user2);
        //when(userInfoRepository.findAll()).thenReturn(userInfoList);
        when(userInfoRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        // 実行
        List<StaffInfo> staffInfos =  itemListServiceImpl.obtainUserIdList();
        // 検証
        assertEquals(2,staffInfos.size());
        assertEquals("id_1",staffInfos.get(0).getUserId());
        assertEquals("name_1",staffInfos.get(0).getUserName());
        assertEquals("id_2",staffInfos.get(1).getUserId());
        assertEquals("name_2",staffInfos.get(1).getUserName());
        // リポジトリ呼び出しが1回だけされたことを確認
        verify(userInfoRepository, times(1)).findAll();
    }
    @Test
    void testEditItemListByParam_NoArrivalStaff() {
        //準備
        ItemSearchInfo itemSearchInfo = new ItemSearchInfo();
        itemSearchInfo.setItemName("pen");
        itemSearchInfo.setArrivalStaff("");

        ItemInfo itemInfo = new ItemInfo();
        itemInfo.setItemName("pen_black");
        when(itemInfoRepository.findByItemNameLike("%pen%")).thenReturn(Arrays.asList(itemInfo));
        //実行
        List<ItemInfo> itemInfos = itemListServiceImpl.editItemListByParam(itemSearchInfo);
        //検証
        assertEquals(1,itemInfos.size());
        assertEquals("pen_black",itemInfos.get(0).getItemName());
        verify(itemInfoRepository, times(1)).findByItemNameLike("%pen%");
    }
    @Test
    void testEditItemListByParam_WithArrivalStaff() {
        ItemSearchInfo itemSearchInfo = new ItemSearchInfo();
        itemSearchInfo.setItemName("pen");
        itemSearchInfo.setArrivalStaff("u001");

        UserInfo user1 = new UserInfo();
        user1.setLoginId("u001");
        user1.setUserName("Tanaka");

        ItemInfo item = new ItemInfo();
        item.setItemName("pen_red");

        when(userInfoRepository.findById("u001")).thenReturn(Optional.of(user1));
        when(itemInfoRepository.findByItemNameLikeAndArrivalStaff("%pen%", "Tanaka")).thenReturn(Arrays.asList(item));
        //実行
        List<ItemInfo> result = itemListServiceImpl.editItemListByParam(itemSearchInfo);
        //検証
        assertEquals(1,result.size());
        assertEquals("pen_red",result.get(0).getItemName());
        verify(userInfoRepository, times(1)).findById("u001");
        verify(itemInfoRepository,times(1)).findByItemNameLikeAndArrivalStaff("%pen%", "Tanaka");
    }
    @Test
    void testEditItemListByParam_WithArrivalStaff_2() {
        ItemSearchInfo itemSearchInfo = new ItemSearchInfo();
        itemSearchInfo.setItemName("pen");
        itemSearchInfo.setArrivalStaff("u001");

        UserInfo user1 = new UserInfo();
        user1.setLoginId("u001");
        user1.setUserName("Tanaka");

        ItemInfo item = new ItemInfo();
        item.setItemName("pen_red");
        ItemInfo item2 = new ItemInfo();
        item2.setItemName("pen_green");

        when(userInfoRepository.findById("u001")).thenReturn(Optional.of(user1));
        when(itemInfoRepository.findByItemNameLikeAndArrivalStaff("%pen%", "Tanaka")).thenReturn(Arrays.asList(item,item2));
        //実行
        List<ItemInfo> result = itemListServiceImpl.editItemListByParam(itemSearchInfo);
        //検証
        assertEquals(2,result.size());
        assertEquals("pen_red",result.get(0).getItemName());
        assertEquals("pen_green",result.get(1).getItemName());
        verify(userInfoRepository, times(1)).findById("u001");
        verify(itemInfoRepository,times(1)).findByItemNameLikeAndArrivalStaff("%pen%", "Tanaka");
    }

    @Test
    void testDeleteItemListByParam_NonExistedItem() {
         Integer deletedItemId = 0;
         when(itemInfoRepository.findById(deletedItemId)).thenReturn(Optional.empty());
         ItemDeleteResult result = itemListServiceImpl.deleteItemInfoById(deletedItemId);
         assertEquals(ItemDeleteResult.ERROR,result);
         verify(itemInfoRepository, times(1)).findById(deletedItemId);
         verify(itemInfoRepository, never()).deleteById(any());
    }
    @Test
    void testDeleteItemListByParam_WithExistedItem() {
        Integer deletedItemId = 1;
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.setItemName("pen");
        itemInfo.setArrivalStaff("u001");
        when(itemInfoRepository.findById(deletedItemId)).thenReturn(Optional.of(itemInfo));
        ItemDeleteResult result = itemListServiceImpl.deleteItemInfoById(deletedItemId);
        assertEquals(ItemDeleteResult.SUCCESS,result);
        verify(itemInfoRepository, times(1)).findById(deletedItemId);
        verify(itemInfoRepository, times(1)).deleteById(deletedItemId);
    }
}
