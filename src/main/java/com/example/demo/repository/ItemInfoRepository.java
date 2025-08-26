package com.example.demo.repository;

import com.example.demo.entity.ItemInfo;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemInfoRepository extends JpaRepository<ItemInfo, String> {
    /**
     * 商品名の部分名一致検索を行います。
     * @param itemName 商品名
     * @return 検索でヒットした商品情報のリスト
     */
    List<ItemInfo> findByItemNameLike(String itemName);

    /**
     * 商品IDの部分一致検索、入荷担当者の検索を行います。
     * @param itemName　商品名
     * @param arrivalStaff　入荷担当者
     * @return 検索でヒットした商品情報のリスト
     */
    List<ItemInfo> findByItemNameLikeAndArrivalStaff(String itemName, String arrivalStaff);
}
