package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 商品情報テーブルEntityクラス
 */
@Data
@Entity
@Table(name = "item_info")
public class ItemInfo {

    @Id
    @Column(name = "item_id")
    private String itemId; // 主キー

    @Column(name = "item_name", nullable = false, length = 255)
    private String itemName; // 商品名

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price; // 単価

    @Column(name = "arrival_date")
    private LocalDate arrivalDate; // 入荷日

    @Column(name = "arrival_staff", length = 100)
    private String arrivalStaff; // 入荷担当者

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 登録日時

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // 最終更新日時

    @Column(name = "updated_by", length = 100)
    private String updatedBy; // 最終更新ユーザ
}