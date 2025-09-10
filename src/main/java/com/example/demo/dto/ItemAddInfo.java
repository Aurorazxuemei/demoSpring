package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ItemAddInfo {

    /**
     * 商品名
     */
    private String itemName;

    /**
     * 入荷担当者
     */
    private String arrivalStaff;

    /**
     * 単価
     */
    private BigDecimal price;

    /**
     * 入荷日
     */
    private LocalDate arrivalDate;

}
