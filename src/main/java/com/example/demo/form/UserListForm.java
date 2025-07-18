package com.example.demo.form;
import com.example.demo.constant.AuthorityKind;
import com.example.demo.constant.UserStatusKind;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

/**
 * ユーザー登録画面Formクラス
 *
 * @author 張
 *
 */
@Data
public class UserListForm {

    /** ログインID */
    @Length(min = 8, max = 20)
    private String loginId;

    /** アカウント状態種別 */
    private UserStatusKind userStatusKind;

    /** ユーザー権限種別 */
    private AuthorityKind authorityKind;

    /**ユーザー一覧情報から選択されたログインID*/
    private String selectedLoginId;

    /**
     * ユーザー一覧情報から選択されたログインIDをクリアします。
     *
     * @return ユーザー一覧情報から選択されたログインIDクリア後のインスタンス
     */
    public UserListForm clearSelectedLoginId(){
        this.selectedLoginId = null;
        return this;
    }
}

