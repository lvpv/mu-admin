package com.lvpb.mu.exception;

import com.lvpb.mu.common.code.CommonCode;
import com.lvpb.mu.common.code.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 21:44
 * @description BusinessException
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 3478904645230024317L;


    /**
     * 业务异常码
     */
    private int code;


    public BusinessException(int code,String message){
        super(message);
        this.code = code;
    }

    private BusinessException(String message) {
        super(message);
        this.code = CommonCode.SERVE_ERROR.getCode();
    }

    public BusinessException(String message,Throwable throwable){
        super(message,throwable);
        this.code = CommonCode.SERVE_ERROR.getCode();
    }

    public BusinessException(ResultCode resultCode){
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public BusinessException(ResultCode resultCode,Throwable throwable){
        super(resultCode.getMessage(),throwable);
        this.code = resultCode.getCode();
    }

}
