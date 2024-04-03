package com.lvpb.mu.exception;

import com.lvpb.mu.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 21:46
 * @description GlobalExceptionHandler
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理系统异常(兜底异常)
     *
     * @param e 系统异常
     * @return 统一返回结果
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handlerException(Exception e) {
        log.error("[exception]", e);
        return Result.error();
    }

    /**
     * 处理自定义业务异常
     *
     * @param e 自定义业务异常
     * @return 统一返回结果
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBindException(BusinessException e) {
        log.error("[BusinessException]", e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理缺失请求体异常
     * 如接口为 login(@RequestBody LoginRequest request),请求时未传递参数
     *
     * @param e 缺失请求体异常
     * @return 统一返回结果
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Void> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("[HttpMessageNotReadableException]", e);
        return Result.error(ErrorCode.REQUEST_PARAMS_NOT_EXIST);
    }
    /**
     * 处理缺失请求参数异常
     * 如接口为 login(@RequestParam String name),请求时未传递name参数
     *
     * @param e 缺失请求体异常
     * @return 统一返回结果
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Void> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("[MissingServletRequestParameterException]", e);
        return Result.error(ErrorCode.REQUEST_PARAMS_NOT_EXIST);
    }

    /**
     * 处理请求参数类型异常
     * 如接口为 login(@RequestParam Long id),请求时传递id=abc
     *
     * @param e 缺失请求体异常
     * @return 统一返回结果
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Void> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("[MethodArgumentTypeMismatchException]", e);
        return Result.error(ErrorCode.REQUEST_PARAMS_TYPE_ERROR);
    }
    /**
     * 处理参数校验异常
     * LoginRequest{
     *     private String username;
     *     private String password;
     * }
     * 如接口为 login(@RequestBody LoginRequest request),请求时未传递参数为{},或者缺少username或password
     *
     * @param e 缺失请求体异常
     * @return 统一返回结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException]", e);
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
            return Result.error(ErrorCode.REQUEST_PARAMS_NOT_EXIST.getCode(), message);
        }
        return Result.error(ErrorCode.REQUEST_PARAMS_NOT_EXIST);
    }
}
