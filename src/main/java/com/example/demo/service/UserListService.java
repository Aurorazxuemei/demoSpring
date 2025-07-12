package com.example.demo.service;

import com.example.demo.dto.UserListInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserListService {
    /**
     * ユーザー情報テーブルを全件検索し、ユーザー一覧画面に必要な情報へ変換して返却します。
     * @return ユーザー情報テーブルの全登録情報
     *
     */
    public List<UserListInfo> editUserList();
}
