package com.lvpb.mu.modules.system.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 22:03
 * @description UserResponse
 */

@Data
public class UserResponse {

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
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 性别(0:未知,1:男,2:女)
     */
    @Schema(description = "性别")
    private Integer gender;

    /**
     * 系统管理员(0:否,1:是)
     */
    @Schema(description = "系统管理员")
    private Boolean admin;

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 状态(0:正常,1:禁用)
     */
    @Schema(description = "状态")
    private Boolean status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;
}
