package com.example.demo.service;

import com.example.demo.constant.ItemEditMessage;
import com.example.demo.dto.ItemEditResult;
import com.example.demo.dto.ItemUpdateInfo;
import com.example.demo.entity.ItemInfo;
import com.example.demo.repository.ItemInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemEditServiceImpl implements ItemEditService {
    private final ItemInfoRepository repository;
    @Override
    public Optional<ItemInfo> serchItemInfo(Integer itemId){
        return repository.findById(itemId);

    }
    @Override
    public ItemEditResult updateItemInfo(ItemUpdateInfo itemUpdateInfo){
        var itemEditResult = new ItemEditResult();
        //現在の登録情報を取得
        var updateInfoOpt = repository.findById(itemUpdateInfo.getItemId());
        if(updateInfoOpt.isEmpty()){
            itemEditResult.setUpdateMessage(ItemEditMessage.FAILED);
            return itemEditResult;
        }
        //画面の入力情報をセット
        var updateInfo = updateInfoOpt.get();
        updateInfo.setItemName(itemUpdateInfo.getItemName());
        updateInfo.setPrice(itemUpdateInfo.getPrice());
        updateInfo.setArrivalStaff(itemUpdateInfo.getArrivalStaff());
        updateInfo.setArrivalDate(itemUpdateInfo.getArrivalDate());
        updateInfo.setUpdatedAt(LocalDateTime.now());
        try{
            repository.save(updateInfo);
        }catch (Exception e){
            itemEditResult.setUpdateMessage(ItemEditMessage.FAILED);
            return itemEditResult;
        }
        itemEditResult.setUpdateItemInfo(updateInfo);
        itemEditResult.setUpdateMessage(ItemEditMessage.SUCCEED);
        return itemEditResult;
    }

}
