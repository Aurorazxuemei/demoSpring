package com.example.demo;

import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import com.example.demo.dto.UserListInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.UserListService;
import com.example.demo.service.UserListServiceImpl;
import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.constant.AuthorityKind.ITEM_WATCHER;
import static com.example.demo.constant.UserStatusKind.ENABLED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserListServiceTest {
    @Mock
    private Mapper mapper;
    @InjectMocks
    private UserListServiceImpl service;
    @Test
    void testToUserListInfos_正常系() {
        //準備
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setUserStatusKind(UserStatusKind.ENABLED);
        userInfo1.setAuthorityKind(AuthorityKind.ITEM_WATCHER);
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setUserStatusKind(UserStatusKind.ENABLED);
        userInfo2.setAuthorityKind(AuthorityKind.ITEM_WATCHER);
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(userInfo1);
        userInfos.add(userInfo2);
        UserListInfo dto1 = new UserListInfo();
        dto1.setStatus("利用可能");
        dto1.setAuthority("商品情報の確認が可能");

        UserListInfo dto2 = new UserListInfo();
        dto2.setStatus("利用可能");
        dto2.setAuthority("商品情報の確認が可能");
        when(mapper.map(userInfo1, UserListInfo.class)).thenReturn(dto1);
        when(mapper.map(userInfo2, UserListInfo.class)).thenReturn(dto2);
        //実行
        List<UserListInfo> userListInfos = service.toUserListInfos(userInfos);
        //検証
        assertEquals(2, userListInfos.size());
        assertEquals("利用可能", userListInfos.get(0).getStatus());
        assertEquals("商品情報の確認が可能",userListInfos.get(0).getAuthority());
    }
}
