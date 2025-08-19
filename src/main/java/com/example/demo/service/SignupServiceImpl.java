package com.example.demo.service;

import com.example.demo.constant.MessageConst;
import com.example.demo.constant.SignupResult;
import com.example.demo.dto.SignupInfo;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.common.MailSendService;
import com.example.demo.util.AppUtil;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

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
     * メール送信
     */
    private final MailSendService service;
    /**
     * メッセージソース
     */
    private final MessageSource messageSource;
    /**
     * ワンタイムコード有効時間
     */
    @Value("${one-time-code.valid-time}")
    private Duration oneTimeCodeValidTime = Duration.ZERO;


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
        //生成された4桁のワンタイムコード（例: "4821"）
        String oneTimeCode = generateOneTimeCode();
        var signupInfo = editSignupInfo(oneTimeCode,dto);

        try {
            repository.save(signupInfo);
        } catch (Exception e) {
            return SignupResult.FAILURE_BY_DB_ERROR;
        }


        //メール送信
        // メールのテンプレート文字列（{0}, {1} のプレースホルダを含む
        var mailTextBase = AppUtil.getMessage(messageSource, MessageConst.SIGNUP_MAIL_TEXT);

        // MessageFormat を使って、メールテンプレートと動的パラメータを組み合わせ、最終的なメール本文を作成する
        // oneTimeCodeValidTime.toMinutes() : ワンタイムコードの有効時間を分単位に変換（例: 3分）
        //生成される mailText の例: "ユーザーの仮登録が完了しました。\n本登録に必要なワンタイムコードは下記になります。\n\n4821\n\n※このリンクは3分間有効です。"
        var mailText = MessageFormat.format(mailTextBase, oneTimeCode, oneTimeCodeValidTime.toMinutes());
        var mailSubject = AppUtil.getMessage(messageSource, MessageConst.SIGNUP_MAIL_SUBJECT);
        boolean canSendMail = service.sendMail(dto.getMailAddress(), mailSubject, mailText);
        if (!canSendMail) {
            return SignupResult.FAILURE_BY_MAIL_SEND_ERROR;
        }
        return SignupResult.SUCCEED;
    }


    public static String generateOneTimeCode() {
        Random random = new Random();
        int code = random.nextInt(10000); // 0〜9999 の範囲
        return String.format("%04d", code); // ゼロ埋めして4桁に
    }

    private UserInfo editSignupInfo(String oneTimeCode, SignupInfo dto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginId(dto.getLoginId());
        userInfo.setMailAddress(dto.getMailAddress());
        userInfo.setOneTimeCode(passwordEncoder.encode(oneTimeCode));
        userInfo.setPassword(passwordEncoder.encode(dto.getPassword()));
        userInfo.setSignupCompleted(false);
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfo.setUpdateUser(dto.getLoginId());
        userInfo.setAuthorityKind(ITEM_WATCHER);
        userInfo.setUserStatusKind(ENABLED);
        userInfo.setOneTimeCodeSendTime(LocalDateTime.now());
        return userInfo;
    }
}