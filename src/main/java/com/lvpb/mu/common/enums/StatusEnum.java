package com.lvpb.mu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 21:36
 * @description StatusEnum
 */

@Getter
@AllArgsConstructor
public enum StatusEnum {
    NORMAL(0, "正常"),
    DISABLED(1, "禁用"),
    ;

    private final int status;

    private final String message;


    public static StatusEnum of(int status) {
        for (StatusEnum value : StatusEnum.values()) {
            if (value.getStatus() == status) {
                return value;
            }
        }
        return null;
    }
}
