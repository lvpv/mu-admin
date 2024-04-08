package com.lvpb.mu.modules.system.controller;

import com.lvpb.mu.common.domain.Result;
import com.lvpb.mu.modules.system.domain.request.LoginRequest;
import com.lvpb.mu.modules.system.domain.response.LoginResponse;
import com.lvpb.mu.modules.system.domain.response.UserResponse;
import com.lvpb.mu.modules.system.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/4 00:32
 * @description AuthController
 */
@Validated
@Tag(name = "认证管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/auth")
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "账号密码登录")
    @PostMapping("/login")
    public Result<LoginResponse> accountLogin(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        LoginResponse response = authService.accountLogin(loginRequest, request);
        return Result.success(response);
    }

    @Operation(summary = "获取登录用户信息")
    @GetMapping("/info")
    public Result<UserResponse> getUserInfo() {
        UserResponse userResponse = authService.getUserInfo();
        return Result.success(userResponse);
    }
}
