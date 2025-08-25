package com.example.demo.service;

import com.example.demo.constant.UserDeleteResult;
import com.example.demo.dto.StaffInfo;
import com.example.demo.dto.UserListInfo;
import com.example.demo.dto.UserSearchInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品一覧画面Serviceインターフェース
 */
public interface ItemListService {
    /**
     * ユーザー情報テーブルを全件検索し、商品一覧画面に必要な情報へ変換して返却します。
     *
     * @return ユーザー情報テーブルの全登録情報
     */
    public List<StaffInfo> obtainUserIdList();


}
