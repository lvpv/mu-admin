package com.lvpb.mu.security;

import com.lvpb.mu.common.domain.Result;
import com.lvpb.mu.exception.ErrorCode;
import com.lvpb.mu.utils.ServletUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 22:33
 * @description AccessDeniedHandlerImpl
 */

@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Long userId = SecurityUtils.getLoginUserId();
        log.warn("[commence][访问 URL({}) 时，用户({}) 权限不够]", request.getRequestURI(), userId, accessDeniedException);
        ServletUtils.writeResult(Result.error(ErrorCode.NOT_HAS_PERMISSION),response);
    }
}
