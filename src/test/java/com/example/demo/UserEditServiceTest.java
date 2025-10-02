package com.example.demo;

import com.example.demo.constant.UserEditMessage;
import com.example.demo.dto.UserEditResult;
import com.example.demo.dto.UserUpdateInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.UserEditServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.demo.constant.AuthorityKind.ITEM_AND_USER_MANAGER;
import static com.example.demo.constant.UserEditMessage.FAILED;
import static com.example.demo.constant.UserStatusKind.DISABLED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEditServiceTest {
    @Mock
    private UserInfoRepository repository;

    @InjectMocks
    private UserEditServiceImpl service;

    @Test
    void updateUser_whenUserNotFound() {
        //準備
        UserUpdateInfo userUpdateInfo = new UserUpdateInfo();
//        userUpdateInfo.setLoginId("User001");
//        userUpdateInfo.setResetsLoginFailure(false);
//        userUpdateInfo.setUserStatusKind(DISABLED);
//        userUpdateInfo.setAuthorityKind(ITEM_AND_USER_MANAGER);
        userUpdateInfo.setLoginId("notExistUser");
        when(repository.findById("notExistUser")).thenReturn(Optional.empty());
        //実行
        UserEditResult result = service.updateUserInfo(userUpdateInfo);
        //検証
        assertEquals(UserEditMessage.FAILED, result.getUpdateMessage());
        assertNull(result.getUpdateUserInfo()); // updateUserInfoはセットされないはず
        verify(repository, never()).save(any()); // save は呼ばれないはず
    }

    @Test
    void testUpdateUserInfo_SaveSuccess(){
        //準備
        UserUpdateInfo userUpdateInfo = new UserUpdateInfo();
        userUpdateInfo.setLoginId("User001");
        userUpdateInfo.setResetsLoginFailure(true);
        userUpdateInfo.setUserStatusKind(DISABLED);
        userUpdateInfo.setAuthorityKind(ITEM_AND_USER_MANAGER);
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginId("User001");
        userInfo.setUserName("User001");
        when(repository.findById("User001")).thenReturn(Optional.of(userInfo));
        //実行
        UserEditResult result = service.updateUserInfo(userUpdateInfo);
        //検証
        assertEquals(UserEditMessage.SUCCEED, result.getUpdateMessage());
        assertNotNull(result.getUpdateUserInfo());
        assertEquals(DISABLED, result.getUpdateUserInfo().getUserStatusKind());
        assertEquals(ITEM_AND_USER_MANAGER, result.getUpdateUserInfo().getAuthorityKind());
        assertEquals(0, result.getUpdateUserInfo().getLoginFailureCount());
        assertEquals(null, result.getUpdateUserInfo().getAccountLockedTime());
        //assertEquals(LocalDateTime.now(), result.getUpdateUserInfo().getUpdateTime());
        assertNotNull(result.getUpdateUserInfo().getUpdateTime());
        verify(repository, times(1)).save(any());
        verify(repository,times(1)).findById(any());
    }
    @Test
    void testUpdateUserInfo_SaveThrowsException() {
        // 準備
        UserUpdateInfo userUpdateInfo = new UserUpdateInfo();
        userUpdateInfo.setLoginId("User001");
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginId("User001");

        when(repository.findById("User001")).thenReturn(Optional.of(userInfo));
        when(repository.save(any(UserInfo.class))).thenThrow(new RuntimeException("DB error"));

        // 実行
        UserEditResult result = service.updateUserInfo(userUpdateInfo);

        // 検証
        assertEquals(UserEditMessage.FAILED, result.getUpdateMessage());
        assertNull(result.getUpdateUserInfo());
    }
}
