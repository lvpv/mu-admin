package com.lvpb.mu.security;

import com.lvpb.mu.common.domain.Result;
import com.lvpb.mu.exception.ErrorCode;
import com.lvpb.mu.utils.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 22:32
 * @description AuthenticationEntryPointImpl
 */
@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.debug("[commence][访问 URL({}) 时，没有登录]", request.getRequestURI(), authException);
        ServletUtils.writeResult(Result.error(ErrorCode.NOT_LOGIN),response);
    }
}
