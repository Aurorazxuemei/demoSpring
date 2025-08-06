package com.example.demo.service;

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
    /** ユーザー情報テーブルRepositoryクラス */
    public final UserInfoRepository repository;
    /** Dozer Mapper */
    private final Mapper mapper;
    /** パスワードエンコーダー */
    private final PasswordEncoder passwordEncoder;

    /**

     * 画面の入力情報を元にユーザー情報テーブルの新規登録を行います。
     *
     * <p>ただし、以下のテーブル項目はこの限りではありません。
     * <ul>
     * <li>パスワード：画面で入力したパスワードがハッシュ化され登録されます。</li>
     * <li>権限：常に「商品情報の確認が可能」のコード値が登録されます。</li>
     * </ul>
     * @param form　入力情報
     * {@code @return　登録情報（ユーザー情報Entity）,既に同じユーザーIDで登録がある場合はEmpty
     *
     */
    @Override
    public Optional<UserInfo> registerUserInfo(SignupForm form){
        var userInfoExistedOpt =repository.findById(form.getLoginId());
        if(userInfoExistedOpt.isPresent()){
            return Optional.empty();
        }
        var userInfo = mapper.map(form, UserInfo.class);
        String encodedpassword = passwordEncoder.encode(form.getPassword());
        userInfo.setPassword(encodedpassword);
        //NullPointerExceptionの対応
        userInfo.setAuthorityKind(ITEM_WATCHER);
        userInfo.setUserStatusKind(ENABLED);
        return Optional.of(repository.save(userInfo));
    }
}
