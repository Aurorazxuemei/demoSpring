package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum UserStatusKind {
    ENABLED(false, "利用可能"),
    DISABLED(true, "利用不可");
    private boolean isDisabled;
    private String displayValue;
    UserStatusKind(boolean isDisabled, String displayValue) {
        this.isDisabled = isDisabled;
        this.displayValue = displayValue;
    }
}
