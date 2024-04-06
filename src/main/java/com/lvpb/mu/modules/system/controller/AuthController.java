package com.lvpb.mu.modules.system.controller;

import com.lvpb.mu.common.domain.Result;
import com.lvpb.mu.config.properties.SecurityProperties;
import com.lvpb.mu.security.TokenService;
import com.lvpb.mu.utils.IpUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/4 00:32
 * @description AuthController
 */

@Tag(name = "认证管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/auth")
public class AuthController {

    private final TokenService tokenService;

    private final SecurityProperties securityProperties;

    private final RedisTemplate<String, Object> redisTemplate;


    @Operation(summary = "账号密码登录")
    @PostMapping("/login")
    public Result<String> accountLogin(HttpServletRequest request) {
        String ipAddr = IpUtils.getIpAddr(request);
        String addressByIp = IpUtils.getAddressByIp(ipAddr);
        System.out.println("IP地址：" + ipAddr + "，归属地：" + addressByIp);
        String token = tokenService.generateAccessToken(1L, "admin");
        String refreshToken = tokenService.generateRefreshToken(1L, "admin");

        String tokenKey = "system:token:" + 1L;
        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("accessToken", token);
        tokenInfo.put("tokenExpire", securityProperties.getTokenExpire());
        tokenInfo.put("refreshToken", refreshToken);
        tokenInfo.put("refreshTokenExpire", securityProperties.getRefreshTokenExpire());
        redisTemplate.opsForValue().set(tokenKey, tokenInfo, securityProperties.getTokenExpire(), TimeUnit.SECONDS);
        String username = tokenService.parseToken(token);
        return Result.success(username);
    }

    @Operation(summary = "验证token")
    @GetMapping("/valid")
    @Parameter(name = "token", description = "token", required = true, in = ParameterIn.QUERY, example = "xxxx")
    public Result<String> validate(@RequestParam String token) {
        String username = tokenService.parseToken(token);
        return Result.success(username);
    }
}
