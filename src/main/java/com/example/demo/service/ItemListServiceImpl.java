package com.example.demo.service;

import com.example.demo.constant.ItemDeleteResult;
import com.example.demo.constant.UserDeleteResult;
import com.example.demo.dto.*;
import com.example.demo.entity.ItemInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.ItemInfoRepository;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.eclipse.angus.mail.imap.protocol.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemListServiceImpl implements ItemListService {
    /**
     * ユーザー情報テーブルDAO
     */
    private final UserInfoRepository userInfoRepository;
    /**
     * 商品情報テーブルDAO
     */
    private final ItemInfoRepository itemInfoRepository;

    @Override
    public List<StaffInfo> obtainUserIdList() {
        var UserInfos = userInfoRepository.findAll();
        List<StaffInfo> staffInfos = new ArrayList<>();
        for (UserInfo userInfo : UserInfos) {
            var staffInfo = new StaffInfo();
            staffInfo.setUserId(userInfo.getLoginId());
            staffInfo.setUserName(userInfo.getUserName());
            staffInfos.add(staffInfo);
        }
        return staffInfos;
    }

    @Override
    public List<ItemInfo> editItemListByParam(ItemSearchInfo dto) {
        String itemName = AppUtil.addWildcard(dto.getItemName());
        // 入荷担当者が選択されていない時
        if (StringUtils.isEmpty(dto.getArrivalStaff())) {
            return itemInfoRepository.findByItemNameLike(itemName);
        } else {
            var UserInfo = userInfoRepository.findById(dto.getArrivalStaff());
            var staffInfo = new StaffInfo();
            staffInfo.setUserName(UserInfo.get().getUserName());
            return itemInfoRepository.findByItemNameLikeAndArrivalStaff(itemName,staffInfo.getUserName());
        }
    }

    @Override
    public ItemDeleteResult deleteItemInfoById(Integer deletedItemId){
        var itemInfo = itemInfoRepository.findById(deletedItemId);
        if (itemInfo.isEmpty()) {
            return ItemDeleteResult.ERROR;
        }
        itemInfoRepository.deleteById(deletedItemId);
        return ItemDeleteResult.SUCCESS;
}
    }