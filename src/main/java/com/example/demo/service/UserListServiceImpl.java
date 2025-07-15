package com.example.demo.service;

import com.example.demo.dto.UserListInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.UserListForm;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.util.AppUtil;
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
    private final UserInfoRepository userInfoRepository;

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
            userListInfo.setStatus(userInfo.getUserStatusKind().getDisplayValue());
            userListInfo.setAuthority(userInfo.getAuthorityKind().getDisplayValue());
            userListInfos.add(userListInfo);

        }
        return userListInfos;
    }

    @Override
    public List<UserListInfo> editUserListByParam(UserListForm form) {
        var userInfo = mapper.map(form, UserInfo.class);
        return toUserListInfos(findUserInfoByParam(userInfo));
    }

    private List<UserInfo> findUserInfoByParam(UserInfo userInfo) {
        var loginIdParam = AppUtil.addWildcard(userInfo.getLoginId());
        if (userInfo.getUserStatusKind() != null && userInfo.getAuthorityKind() != null) {
            return repository.findByLoginIdLikeAndUserStatusKindAndAuthorityKind(loginIdParam, userInfo.getUserStatusKind(), userInfo.getAuthorityKind());
        } else if (userInfo.getUserStatusKind() != null) {
            return repository.findByLoginIdLikeAndUserStatusKind(loginIdParam, userInfo.getUserStatusKind());
        } else if (userInfo.getAuthorityKind() != null) {
            return repository.findByLoginIdLikeAndAuthorityKind(loginIdParam, userInfo.getAuthorityKind());
        } else {
            return repository.findByLoginIdLike(loginIdParam);
        }
    }
}