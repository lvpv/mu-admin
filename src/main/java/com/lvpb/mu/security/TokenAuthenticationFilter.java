package com.lvpb.mu.security;

import cn.hutool.core.util.StrUtil;
import com.lvpb.mu.exception.BusinessException;
import com.lvpb.mu.exception.ErrorCode;
import com.lvpb.mu.modules.system.constant.SystemCacheKey;
import com.lvpb.mu.utils.ServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/4 00:07
 * @description TokenAuthenticationFilter
 */
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenService.getAccessToken(request);
        if (StrUtil.isBlank(accessToken)){
            filterChain.doFilter(request,response);
            return;
        }
       try {
           Long userId = jwtTokenService.getUserId(accessToken);
           if (Objects.isNull(userId)){
               ServletUtils.writeError(ErrorCode.TOKEN_IS_EXPIRED,response);
               return;
           }
           String loginUserCacheKey = SystemCacheKey.LOGIN_USER_CACHE_KEY.formatted(userId);
           Object cacheUser = redisTemplate.opsForValue().get(loginUserCacheKey);
           if (Objects.isNull(cacheUser)){
               ServletUtils.writeError(ErrorCode.TOKEN_IS_EXPIRED,response);
               return;
           }
           AuthUser authUser = (AuthUser) cacheUser;
           Authentication authentication = new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
           // 新建 SecurityContext
           SecurityContext context = SecurityContextHolder.createEmptyContext();
           context.setAuthentication(authentication);
           SecurityContextHolder.setContext(context);
           filterChain.doFilter(request, response);
       }catch (BusinessException e){
           ServletUtils.writeError(e.getCode(),e.getMessage(),response);
       }

    }
}
