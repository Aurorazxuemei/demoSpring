package com.example.demo.form;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * 商品登録画面Form
 */
@Data
public class ItemAddForm {

    // 商品名（必須・255文字以内・全角/半角文字許可）
    @NotBlank(message = "商品名は必須です")
    @Size(max = 255, message = "商品名は255文字以内で入力してください")
    private String itemName;

    // 単価（整数8桁 + 小数点以下2桁まで）
    @NotNull(message = "単価は必須です")
    @Digits(integer = 8, fraction = 2, message = "単価は整数8桁、小数2桁までです")
    @Pattern(regexp = "^[0-9]{1,8}(\\.[0-9]{1,2})?$", message = "正しい金額形式で入力してください")
    private String price;

    // 入荷日（YYYY-MM-DD の形式）
    @NotNull(message = "入荷日は必須です")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "日付は YYYY-MM-DD の形式で入力してください")
    private LocalDate arrivalDate;

    // 入荷担当者（任意・100文字以内）
    @Size(max = 100, message = "入荷担当者は100文字以内で入力してください")
    private String arrivalStaff;
}
