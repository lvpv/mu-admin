package com.lvpb.mu.modules.system.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/7 23:26
 * @description TokenResponse
 */
@Data
public class TokenResponse {

    /**
     * token编号
     */
    @Schema(description = "token编号")
    private Long id;

    /**
     * 用户编号
     */
    @Schema(description = "用户编号")
    private Long userId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 用户类别(0:普通用户,1:系统管理员)
     */
    @Schema(description = "用户类别(0:普通用户,1:系统管理员)")
    private Boolean userType;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    private String realName;

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
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "accessToken有效时间")
    private Long tokenExpireSecond;

    @Schema(description = "accessToken有效时间")
    private Long refreshTokenExpireSecond;
}
