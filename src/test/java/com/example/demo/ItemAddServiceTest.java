package com.example.demo;

import com.example.demo.constant.ItemAddResult;
import com.example.demo.dto.ItemAddInfo;
import com.example.demo.entity.ItemInfo;
import com.example.demo.repository.ItemInfoRepository;
import com.example.demo.service.ItemAddServiceImpl;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ItemAddServiceTest {
    @Mock
    private ItemInfoRepository repository;
    @Mock
    private Mapper mapper;

    @InjectMocks
    private ItemAddServiceImpl itemAddServiceImpl;
//    public ItemAddServiceTest() {
//        MockitoAnnotations.openMocks(this);
//    }
    @Test
    void testAddItem_succeed() {
        //準備
        ItemAddInfo dto = new ItemAddInfo();
        dto.setItemName("チョコレート");
        dto.setPrice(BigDecimal.valueOf(120));
        dto.setArrivalStaff("tyo");
        dto.setArrivalDate(LocalDate.parse("2025-09-22"));
        ItemInfo mappedItem = new ItemInfo();
        mappedItem.setItemId(1);
        mappedItem.setItemName("クッキー");
        mappedItem.setPrice(BigDecimal.valueOf(120));
        mappedItem.setArrivalStaff("tyo");
        mappedItem.setArrivalDate(LocalDate.parse("2025-09-22"));
        mappedItem.setUpdatedAt(LocalDateTime.parse("2025-09-23T10:15:30"));
        // mapper が返すオブジェクトを指定
        when(mapper.map(dto, ItemInfo.class)).thenReturn(mappedItem);
        // repository.save はどんな ItemInfo でも mappedItem を返す
        when(repository.save(any(ItemInfo.class))).thenReturn(mappedItem);
        //when(repository.save(mappedItem)).thenReturn(mappedItem);
        //実行
        ItemAddResult result = itemAddServiceImpl.addItem(dto);
        //検証
        assertEquals(ItemAddResult.SUCCEED,result);
        verify(repository,times(1)).save(any(ItemInfo.class));

    }

    @Test
    void testAddItem_fail() {
        //準備
        ItemAddInfo dto = new ItemAddInfo();
        dto.setItemName("チョコレート");
        dto.setPrice(BigDecimal.valueOf(120));
        dto.setArrivalStaff("tyo");
        dto.setArrivalDate(LocalDate.parse("2025-09-22"));
        ItemInfo mappedItem = new ItemInfo();
        mappedItem.setItemId(1);
        mappedItem.setItemName("クッキー");
        mappedItem.setPrice(BigDecimal.valueOf(120));
        mappedItem.setArrivalStaff("tyo");
        mappedItem.setArrivalDate(LocalDate.parse("2025-09-22"));
        mappedItem.setUpdatedAt(LocalDateTime.parse("2025-09-23T10:15:30"));
        // mapper が返すオブジェクトを指定
        when(mapper.map(dto, ItemInfo.class)).thenReturn(mappedItem);
        // repository.save はどんな ItemInfo でも mappedItem を返す
        when(repository.save(any(ItemInfo.class))).thenThrow(new RuntimeException("DB error"));
        //when(repository.save(mappedItem)).thenReturn(mappedItem);
        //実行
        ItemAddResult result = itemAddServiceImpl.addItem(dto);
        //検証
        assertEquals(ItemAddResult.FAILURE_BY_DB_ERROR,result);
        verify(repository,times(1)).save(any(ItemInfo.class));

    }
}
