package com.example.demo.service;

import com.example.demo.entity.ItemInfo;
import com.example.demo.repository.ItemInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemEditServiceImpl implements ItemEditService {
    private final ItemInfoRepository itemInfoRepository;
    @Override
    public Optional<ItemInfo> serchItemInfo(String itemId){
        return itemInfoRepository.findById(itemId);

    }

}
