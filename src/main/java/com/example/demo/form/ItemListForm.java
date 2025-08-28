package com.example.demo.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 商品一覧画面Formクラス
 *
 * @author 張
 *
 */
@Data
public class ItemListForm {

    /**
     * 商品名
     */
    @Length(max = 100)
    private String itemName;

    /**
     * 入荷担当者
     */
    @Length(max = 20)
    private String arrivalStaff;

    /**
     * 選択された商品ID
     */
    private String selectedItemId;

}
