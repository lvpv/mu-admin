package com.lvpb.mu.modules.system.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/7 19:09
 * @description LoginRequest Description
 */

@Data
public class LoginRequest {

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
