package com.lvpb.mu.utils;

import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.json.JSONUtil;
import com.lvpb.mu.common.domain.Result;
import com.lvpb.mu.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 21:50
 * @description ServletUtils
 */
public class ServletUtils {

    public static void writeError(ErrorCode errorCode, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        Result<Void> error = Result.error(errorCode);
        String result = JSONUtil.toJsonStr(error);
        JakartaServletUtil.write(response,result, MediaType.APPLICATION_JSON_VALUE);
    }

    public static <T> void writeResult(Result<T> result, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        String data = JSONUtil.toJsonStr(result);
        JakartaServletUtil.write(response,data, MediaType.APPLICATION_JSON_VALUE);
    }
}
