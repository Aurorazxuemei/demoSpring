package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

    @Data
    public class ItemListInfo {
        private Long itemId;
        private String itemName;
        private BigDecimal price;
        private LocalDate arrivalDate;
        private String arrivalStaff;
    }
