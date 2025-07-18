package com.example.demo.service;

import com.example.demo.constant.UserDeleteResult;
import com.example.demo.dto.UserListInfo;
import com.example.demo.dto.UserSerchInfo;
import com.example.demo.form.UserListForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserListService {
    /**
     * ユーザー情報テーブルを全件検索し、ユーザー一覧画面に必要な情報へ変換して返却します。
     *
     * @return ユーザー情報テーブルの全登録情報
     */
    public List<UserListInfo> editUserList();

    /**
     * 検索条件に基づいて、DBから該当するレコードを検索する。
     *
     * @return ユーザー情報テーブルの該当登録情報
     */
    public List<UserListInfo> editUserListByParam(UserSerchInfo dto);

    /**
     * 選択されたユーザー情報を削除する
     * @return 実行結果（エラー有無）
     */
    public UserDeleteResult deleteUserInfoById(String loginId);
}
