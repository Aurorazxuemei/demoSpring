package com.example.demo.service;

import com.example.demo.constant.ItemAddResult;
import com.example.demo.dto.ItemAddInfo;

public interface ItemAddService {
    public ItemAddResult  addItem(ItemAddInfo dto);
}
