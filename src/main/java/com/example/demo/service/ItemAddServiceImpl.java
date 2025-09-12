package com.example.demo.service;

import com.example.demo.constant.ItemAddResult;
import com.example.demo.dto.ItemAddInfo;
import com.example.demo.entity.ItemInfo;
import com.example.demo.repository.ItemInfoRepository;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ItemAddServiceImpl implements ItemAddService {
    private final Mapper mapper;
    private final ItemInfoRepository itemInfoRepository;

    @Override
    public ItemAddResult addItem(ItemAddInfo dto) {
        var itemInfo = mapper.map(dto, ItemInfo.class);
        itemInfo.setCreatedAt(LocalDateTime.now());
        itemInfo.setUpdatedAt(LocalDateTime.now());
        try{
            itemInfoRepository.save(itemInfo);
        }catch(Exception e){
            return ItemAddResult.FAILURE_BY_DB_ERROR;
        }
        return ItemAddResult.SUCCEED;
    }
}
