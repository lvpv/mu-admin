package com.lvpb.mu.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/4 20:55
 * @description LoginResultEnum
 */

@Getter
@AllArgsConstructor
public enum LoginResultEnum {
    SUCCESS(0, "登陆成功"),
    FAIL(1, "登录失败");

    private final int result;

    private final String description;

    public static LoginResultEnum of(int result) {
        for (LoginResultEnum loginResult : LoginResultEnum.values()) {
            if (loginResult.getResult() == result) {
                return loginResult;
            }
        }
        return null;
    }

}
