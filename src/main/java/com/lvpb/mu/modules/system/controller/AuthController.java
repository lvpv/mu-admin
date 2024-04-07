package com.lvpb.mu.modules.system.controller;

import com.lvpb.mu.common.domain.Result;
import com.lvpb.mu.modules.system.domain.request.LoginRequest;
import com.lvpb.mu.modules.system.domain.response.LoginResponse;
import com.lvpb.mu.modules.system.service.AuthService;
import com.lvpb.mu.security.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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

    private final JwtTokenService jwtTokenService;

    private final AuthService authService;

    private final RedisTemplate<String, Object> redisTemplate;


    @Operation(summary = "账号密码登录")
    @PostMapping("/login")
    public Result<LoginResponse> accountLogin(@Valid LoginRequest loginRequest, HttpServletRequest request) {
        LoginResponse response = authService.accountLogin(loginRequest, request);
        return Result.success(response);
    }

    @Operation(summary = "验证token")
    @GetMapping("/valid")
    @Parameter(name = "token", description = "token", required = true, in = ParameterIn.QUERY, example = "xxxx")
    public Result<String> validate(@RequestParam String token) {
        String username = jwtTokenService.parseToken(token);
        return Result.success(username);
    }
}
