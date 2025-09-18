package com.example.demo.service;

import com.example.demo.dto.ItemEditResult;
import com.example.demo.dto.ItemUpdateInfo;
import com.example.demo.entity.ItemInfo;
import com.example.demo.form.ItemEditForm;
import com.example.demo.form.ItemListForm;
import org.springframework.ui.Model;

import java.util.Optional;

public interface ItemEditService {
    Optional<ItemInfo> serchItemInfo(Integer itemId);
    ItemEditResult updateItemInfo(ItemUpdateInfo itemUpdateInfo);
}
