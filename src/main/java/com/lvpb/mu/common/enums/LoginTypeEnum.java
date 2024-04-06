package com.lvpb.mu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/4 20:47
 * @description LoginTypeEnum
 */

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {

    ACCOUNT(1, "账号登录"),
    MOBILE(2, "手机号登录"),
    SOCIAL(3, "社交登录"),
    LOGOUT(0, "退出登录");


    private final int type;

    private final String description;

    public static LoginTypeEnum of(int type) {
        for (LoginTypeEnum loginTypeEnum : LoginTypeEnum.values()) {
            if (loginTypeEnum.getType() == type) {
                return loginTypeEnum;
            }
        }
        return null;
    }
}
