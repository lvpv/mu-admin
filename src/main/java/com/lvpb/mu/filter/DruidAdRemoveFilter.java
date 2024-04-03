package com.lvpb.mu.filter;

import com.alibaba.druid.util.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 23:53
 * @description Druid监控页面广告去除过滤器
 */
public class DruidAdRemoveFilter extends OncePerRequestFilter {

    private static final String COMMON_JS_ILE_PATH = "support/http/resources/js/common.js";

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
        // 重置缓冲区，响应头不会被重置
        response.resetBuffer();
        // 获取 common.js
        String text = Utils.readFromResource(COMMON_JS_ILE_PATH);
        // 正则替换 banner, 除去底部的广告信息
        text = text.replaceAll("<a.*?banner\"></a><br/>", "");
        text = text.replaceAll("powered.*?shrek.wang</a>", "");
        response.getWriter().write(text);
    }
}
