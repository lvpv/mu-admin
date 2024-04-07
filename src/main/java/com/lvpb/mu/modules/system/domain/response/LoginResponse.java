package com.lvpb.mu.modules.system.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/7 23:36
 * @description LoginResponse
 */

@Data
public class LoginResponse {

    /**
     * AccessToken
     */
    @Schema(description = "AccessToken")
    private String accessToken;

    /**
     * AccessToken过期时间
     */
    @Schema(description = "AccessToken过期时间")
    private Date accessTokenExpires;

    /**
     * RefreshToken
     */
    @Schema(description = "RefreshToken")
    private String refreshToken;

    /**
     * RefreshToken过期时间
     */
    @Schema(description = "RefreshToken过期时间")
    private Date refreshTokenExpires;
}
