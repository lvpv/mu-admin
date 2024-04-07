package com.lvpb.mu.modules.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lvpb.mu.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/7 15:38
 * @description 系统管理-Token表
 */

@Schema(description = "系统管理-Token表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "system_token")
public class Token extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1420655980323743013L;

    /**
     * token编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "token编号")
    private Long id;

    /**
     * 用户编号
     */
    @TableField(value = "user_id")
    @Schema(description = "用户编号")
    private Long userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @Schema(description = "用户名")
    private String username;

    /**
     * 用户类别(0:普通用户,1:系统管理员)
     */
    @TableField(value = "user_type")
    @Schema(description = "用户类别(0:普通用户,1:系统管理员)")
    private Boolean userType;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * AccessToken
     */
    @TableField(value = "access_token")
    @Schema(description = "AccessToken")
    private String accessToken;

    /**
     * AccessToken过期时间
     */
    @TableField(value = "access_token_expires")
    @Schema(description = "AccessToken过期时间")
    private Date accessTokenExpires;

    /**
     * RefreshToken
     */
    @TableField(value = "refresh_token")
    @Schema(description = "RefreshToken")
    private String refreshToken;

    /**
     * RefreshToken过期时间
     */
    @TableField(value = "refresh_token_expires")
    @Schema(description = "RefreshToken过期时间")
    private Date refreshTokenExpires;
}