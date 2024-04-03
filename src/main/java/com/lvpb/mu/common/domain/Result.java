package com.lvpb.mu.common.domain;

import com.lvpb.mu.common.code.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

import static com.lvpb.mu.common.code.CommonCode.SERVE_ERROR;
import static com.lvpb.mu.common.code.CommonCode.SUCCESS;


/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/3/29 22:53
 * @description Result
 */

@Data
@Schema(name = "Result",description = "统一返回数据包装类")
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -6028573577990094291L;

    /**
     * 响应状态码
     */
    @Schema(description = "响应状态码")
    private Integer code;

    /**
     * 响应信息
     */
    @Schema(description = "响应信息")
    private String message;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;


    public static <T> Result<T> success(T data) {
        return getInstance(SUCCESS.getCode(), SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success(ResultCode resultCode, T data) {
        return getInstance(resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static Result<Void> error() {
        return getInstance(SERVE_ERROR.getCode(), SERVE_ERROR.getMessage(), null);
    }
    public static Result<Void> error(ResultCode resultCode) {
        return getInstance(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static Result<Void> error(Integer code, String message) {
        return getInstance(code, message, null);
    }

    public static Result<String> error(ResultCode resultCode, String data) {
        return getInstance(resultCode.getCode(), resultCode.getMessage(), data);
    }

    private static <T> Result<T> getInstance(Integer code, String message, T data) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }
}
