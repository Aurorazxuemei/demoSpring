package com.example.demo.service;

import com.example.demo.constant.ItemDeleteResult;
import com.example.demo.constant.UserDeleteResult;
import com.example.demo.dto.*;
import com.example.demo.entity.ItemInfo;
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
    /**
     * 検索条件に基づいて、DBから該当するレコードを検索する。
     *
     * @return 商品情報テーブルの該当登録情報
     */
    public List<ItemInfo> editItemListByParam(ItemSearchInfo dto);
    /**
     * 画面選択された行を削除する
     *
     * @return 削除結果
     */
    public ItemDeleteResult deleteItemInfoById(Integer deletedItemId);
}
