package com.example.demo.form;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 商品編集画面Form
 */
@Data
public class ItemEditForm {
    /** 商品名*/
    private String itemName;

    /** 入荷担当者 */
    private String arrivalStaff;

    /**単価*/
    private BigDecimal price;

    /** 入荷日 */
    private LocalDate arrivalDate;

}
