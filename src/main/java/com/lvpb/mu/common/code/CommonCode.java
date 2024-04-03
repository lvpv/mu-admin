package com.lvpb.mu.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/3/29 23:00
 * @description CommonCode
 */

@Getter
@AllArgsConstructor
public enum CommonCode implements ResultCode{
    SUCCESS(20000,"操作成功！"),
    SERVE_ERROR(50000,"服务器异常，请稍后再试！"),
    ;

    private final int code;

    private final String message;
}
