package com.example.demo.service;

import com.example.demo.constant.UserDeleteResult;
import com.example.demo.dto.StaffInfo;
import com.example.demo.dto.UserListInfo;
import com.example.demo.dto.UserSearchInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.eclipse.angus.mail.imap.protocol.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemListServiceImpl implements ItemListService {
    private final UserInfoRepository repository;

    @Override
    public List<StaffInfo> obtainUserIdList() {
        var UserInfos = repository.findAll();
        List<StaffInfo> staffInfos = new ArrayList<>();
        for (UserInfo userInfo : UserInfos) {
            var staffInfo = new StaffInfo();
            staffInfo.setUserId(userInfo.getLoginId());
            staffInfo.setUserName(userInfo.getUserName());
            staffInfos.add(staffInfo);
        }
        return staffInfos;
    }
}