package com.lvpb.mu.exception;

import com.lvpb.mu.common.code.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 21:45
 * @description ErrorCode
 */

@Getter
@AllArgsConstructor
public enum ErrorCode implements ResultCode {

    // Serve
    REQUEST_PARAMS_NOT_EXIST(400001,"缺少请求参数！"),
    REQUEST_PARAMS_IS_NULL(400002,"请求参数为空！"),
    REQUEST_PARAMS_TYPE_ERROR(400003,"请求参数类型错误！"),
    REQUEST_PARAMS_ERROR(400004,"请求参数错误！"),
    REQUEST_DATE_NOT_EXIST(400005,"请求数据不存在！"),
    NOT_LOGIN(401000,"账号未登录！"),
    NOT_HAS_PERMISSION(403000,"没有该操作权限！"),
    // Auth
    USERNAME_PASSWORD_ERROR(401001,"用户名或密码错误！"),
    USER_IS_DISABLED(401002,"账号已被禁用，请联系管理员！"),
            ;



    private final int code;

    private final String message;
}
