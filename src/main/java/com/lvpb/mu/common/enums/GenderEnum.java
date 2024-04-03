package com.lvpb.mu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 21:39
 * @description GenderEnum
 */

@Getter
@AllArgsConstructor
public enum GenderEnum {
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(0, "未知");

    private final int gender;

    private final String description;

    public static GenderEnum of(int gender) {
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getGender() == gender) {
                return genderEnum;
            }
        }
        return null;
    }
}
