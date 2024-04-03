package com.lvpb.mu.modules.system.controller;

import com.lvpb.mu.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/4 00:32
 * @description AuthController
 */

@Tag(name = "认证管理")
@RestController
@RequestMapping("/system/auth")
public class AuthController {


    @Operation(summary = "账号密码登录")
    @PostMapping("/login")
    public Result<Void> accountLogin() {
        return Result.success(null);
    }
}
