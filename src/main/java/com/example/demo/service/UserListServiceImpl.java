package com.example.demo.service;

import com.example.demo.dto.UserListInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.github.dozermapper.core.Mapper;

@Service
@RequiredArgsConstructor
public class UserListServiceImpl implements UserListService {
    private final UserInfoRepository repository;
    private final Mapper mapper;

    @Override
    public List<UserListInfo> editUserList() {
        return toUserListInfos(repository.findAll());
    }

    /**
     * ユーザー情報EntityのListをユーザー一覧情報のDTOに変換します。
     *
     * @param userInfos 　ユーザー情報EntityのList
     * @return　ユーザー一覧情報DTOのList
     */
    private List<UserListInfo> toUserListInfos(List<UserInfo> userInfos) {
        List<UserListInfo> userListInfos = new ArrayList<UserListInfo>();
        for (UserInfo userInfo : userInfos) {
            var userListInfo = mapper.map(userInfo, UserListInfo.class);
            userListInfo.setStatus(userInfo.getStatus().getDisplayValue());
            userListInfo.setAuthority(userInfo.getAuthority().getDisplayValue());
            userListInfos.add(userListInfo);

        }
        return userListInfos;
    }
}