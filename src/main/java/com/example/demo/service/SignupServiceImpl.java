package com.example.demo.service;

import com.example.demo.constant.SignupResult;
import com.example.demo.dto.SignupInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.UserInfoRepository;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.demo.constant.AuthorityKind.ITEM_WATCHER;
import static com.example.demo.constant.UserStatusKind.ENABLED;

@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {
    /**
     * ユーザー情報テーブルRepositoryクラス
     */
    public final UserInfoRepository repository;
    /**
     * Dozer Mapper
     */
    private final Mapper mapper;
    /**
     * パスワードエンコーダー
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 画面の入力情報を元にユーザー情報テーブルの新規登録を行います。
     *
     * <p>ただし、以下のテーブル項目はこの限りではありません。
     * <ul>
     * <li>パスワード：画面で入力したパスワードがハッシュ化され登録されます。</li>
     * <li>権限：常に「商品情報の確認が可能」のコード値が登録されます。</li>
     * </ul>
     *
     * @param dto 入力情報
     *            {@code @return　登録情報（ユーザー情報Entity）,既に同じユーザーIDで登録がある場合はEmpty
     */
    @Override
    public SignupResult signup(SignupInfo dto) {
        var userInfoOpt = repository.findById(dto.getLoginId());
        if (userInfoOpt.isPresent()) {
            var userInfo = userInfoOpt.get();
            if (userInfo.isSignupCompleted()) {
                return SignupResult.FAILURE_BY_ALREADY_COMPLETED;
            }
            return SignupResult.FAILURE_BY_SIGNUP_PROCEEDING;
        }

        return SignupResult.SUCCEED;
    }

}