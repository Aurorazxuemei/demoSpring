package com.example.demo;

import com.example.demo.constant.ItemEditMessage;
import com.example.demo.dto.ItemEditResult;
import com.example.demo.dto.ItemUpdateInfo;
import com.example.demo.entity.ItemInfo;
import com.example.demo.repository.ItemInfoRepository;
import com.example.demo.service.ItemEditServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class ItemServiceTest {

    @Mock
    private ItemInfoRepository repository;

    @InjectMocks
    private ItemEditServiceImpl itemService;

    public ItemServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateItemInfo_success() {
        // given
        ItemUpdateInfo input = new ItemUpdateInfo();
        input.setItemId(1);
        input.setItemName("新しい商品");
        input.setPrice(new BigDecimal("1000"));
        input.setArrivalStaff("山田");
        input.setArrivalDate(LocalDate.of(2025, 9, 1));

        ItemInfo existing = new ItemInfo();
        existing.setItemId(1);
        existing.setItemName("古い商品");
        existing.setPrice(new BigDecimal("500"));

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.save(any(ItemInfo.class))).thenReturn(existing);

        // when
        ItemEditResult result = itemService.updateItemInfo(input);

        // then
        assertThat(result.getUpdateMessage()).isEqualTo(ItemEditMessage.SUCCEED);
        assertThat(result.getUpdateItemInfo().getItemName()).isEqualTo("新しい商品");
        assertThat(result.getUpdateItemInfo().getPrice()).isEqualByComparingTo("1000");
        verify(repository).save(existing);
    }

    @Test
    void updateItemInfo_notFound() {
        // given
        ItemUpdateInfo input = new ItemUpdateInfo();
        input.setItemId(99);

        when(repository.findById(99)).thenReturn(Optional.empty());

        // when
        ItemEditResult result = itemService.updateItemInfo(input);

        // then
        assertThat(result.getUpdateMessage()).isEqualTo(ItemEditMessage.FAILED);
        assertThat(result.getUpdateItemInfo()).isNull();
        verify(repository, never()).save(any());
    }

    @Test
    void updateItemInfo_saveThrowsException() {
        // given
        ItemUpdateInfo input = new ItemUpdateInfo();
        input.setItemId(1);

        ItemInfo existing = new ItemInfo();
        existing.setItemId(1);

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenThrow(new RuntimeException("DB error"));

        // when
        ItemEditResult result = itemService.updateItemInfo(input);

        // then
        assertThat(result.getUpdateMessage()).isEqualTo(ItemEditMessage.FAILED);
        verify(repository).save(existing);
    }

    //@Test
//    void serchItemInfo_success() {
//        ItemUpdateInfo input = new ItemUpdateInfo();
//        input.setItemId(1);
//        ItemInfo existing = new ItemInfo();
//        when(repository.findById(1)).thenReturn(Optional.of(existing));
//        Optional<ItemInfo> itemInfo = itemService.serchItemInfo(input.getItemId());
//        assertThat(itemInfo.get()).isEqualTo(existing);
//    }
    @Test
    void serchItemInfo_found() {
        // given
        ItemInfo item = new ItemInfo();
        item.setItemId(1);
        item.setItemName("テスト商品");

        when(repository.findById(1)).thenReturn(Optional.of(item));

        // when
        Optional<ItemInfo> result = itemService.serchItemInfo(1);

        // then
        assertThat(result).isPresent();  // Optional に値がある
        assertThat(result.get().getItemName()).isEqualTo("テスト商品");
    }

    @Test
    void serchItemInfo_notFound() {
        // given
        when(repository.findById(99)).thenReturn(Optional.empty());

        // when
        Optional<ItemInfo> result = itemService.serchItemInfo(99);

        // then
        assertThat(result).isEmpty();  // Optional が空であること
    }
}

