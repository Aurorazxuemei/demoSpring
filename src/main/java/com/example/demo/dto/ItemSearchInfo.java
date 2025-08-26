package com.example.demo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 商品一覧画面の検索Dto
 */
@Data
public class ItemSearchInfo {
    /** 商品名*/
    private String itemName;

    /** 入荷担当者 */
    private String arrivalStaff;
}
