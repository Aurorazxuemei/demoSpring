package com.example.demo;

import com.example.demo.constant.SignupResult;
import com.example.demo.dto.SignupInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.SignupServiceImpl;
import com.example.demo.service.common.MailSendService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SignupServiceTest {
    @Mock
    private UserInfoRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MailSendService mailSendService;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private SignupServiceImpl service;

    //    @Test
//    void signup_found(){
//        UserInfo  userInfo = new UserInfo();
//        userInfo.setLoginId("user001");
//        userInfo.setPassword("password001");
//        userInfo.setMailAddress("zhangxuemei.19870928@gmail.com");
//        userInfo.setSignupCompleted(true);
//        SignupInfo dto = new SignupInfo();
//        dto.setLoginId("user001");
//        dto.setPassword("password001");
//        dto.setMailAddress("zhangxuemei.19870928@gmail.com");
//        when(repository.findById("user001")).thenReturn(Optional.of(userInfo));
//        SignupResult result = service.signup(dto);
//        assertEquals(SignupResult.FAILURE_BY_ALREADY_COMPLETED,result);
//    }
    @Test
    void testSignup_FailureByAlreadyCompleted() {
        // given
        SignupInfo dto = new SignupInfo("user001", "password001", "zhangxuemei.19870928@gmail.com");
        UserInfo userInfo = new UserInfo();
        userInfo.setSignupCompleted(true);
        when(repository.findById("user001")).thenReturn(Optional.of(userInfo));
        // when
        SignupResult result = service.signup(dto);
        // then
        assertThat(result).isEqualTo(SignupResult.FAILURE_BY_ALREADY_COMPLETED);
    }
    @Test
    void testSignup_FailureBySignupProceeding() {
        // given
        SignupInfo dto = new SignupInfo("user001", "password001", "zhangxuemei.19870928@gmail.com");
        UserInfo userInfo = new UserInfo();
        userInfo.setSignupCompleted(false);
        when(repository.findById("user001")).thenReturn(Optional.of(userInfo));
        // when
        SignupResult result = service.signup(dto);
        // then
        assertThat(result).isEqualTo(SignupResult.FAILURE_BY_SIGNUP_PROCEEDING);
    }

    @Test
    void testSignup_FailureByDbError() {
        //given
        SignupInfo dto =new SignupInfo("user001", "password001", "zhangxuemei.19870928@gmail.com");
//        UserInfo userInfo = new UserInfo();
//        userInfo.setLoginId(dto.getLoginId());
//        userInfo.setMailAddress(dto.getMailAddress());
//        userInfo.setOneTimeCode("1234");
//        userInfo.setPassword("1234");
//        userInfo.setSignupCompleted(false);
//        userInfo.setCreateTime(LocalDateTime.now());
//        userInfo.setUpdateTime(LocalDateTime.now());
//        userInfo.setUpdateUser(dto.getLoginId());
//        userInfo.setAuthorityKind(ITEM_WATCHER);
//        userInfo.setUserStatusKind(ENABLED);
//        userInfo.setOneTimeCodeSendTime(LocalDateTime.now());
        when(repository.findById("user001")).thenReturn(Optional.empty());
        doThrow(new RuntimeException("DB Error")).when(repository).save(any());
        //when(serviceMock.generateOneTimeCode()).thenReturn("1234");
        //when(serviceMock.editSignupInfo("1234",dto)).thenReturn(userInfo);
        //when
        SignupResult result = service.signup(dto);
        //then
        assertThat(result).isEqualTo(SignupResult.FAILURE_BY_DB_ERROR);
    }
    @Test
    void testSignup_FailureByMailSendError() {
        // given
        SignupInfo dto = new SignupInfo("user1", "pass", "mail@test.com");
        when(repository.findById("user1")).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(new UserInfo());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("dummy mail text");
        when(mailSendService.sendMail(anyString(), anyString(), anyString())).thenReturn(false);
        // when
        SignupResult result = service.signup(dto);
        // then
        assertThat(result).isEqualTo(SignupResult.FAILURE_BY_MAIL_SEND_ERROR);
    }
    @Test
    void testSignup_Succeed() {
        // given
        SignupInfo dto = new SignupInfo("user1", "pass", "mail@test.com");
        when(repository.findById("user1")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(repository.save(any())).thenReturn(new UserInfo());
        when(messageSource.getMessage(anyString(), any(), any())).thenReturn("メール本文 {0} {1}");
        when(mailSendService.sendMail(anyString(), anyString(), anyString())).thenReturn(true);
        // when
        SignupResult result = service.signup(dto);
        // then
        assertThat(result).isEqualTo(SignupResult.SUCCEED);
        verify(repository, times(1)).save(any());
    }
}
