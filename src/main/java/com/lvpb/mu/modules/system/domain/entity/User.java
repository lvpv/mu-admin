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
 * @date 2024/4/3 20:48
 * @description 系统管理-用户表
 * @version 1.0.0
 */

@Schema(description = "系统管理-用户表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "`system_user`")
public class User extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 585098103691908970L;

    /**
     * 用户编号
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @Schema(description = "用户编号")
    private Long userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    @Schema(description = "密码")
    private String password;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @Schema(description = "头像")
    private String avatar;

    /**
     * 性别(0:未知,1:男,2:女)
     */
    @TableField(value = "gender")
    @Schema(description = "性别(0:未知,1:男,2:女)")
    private Integer gender;

    /**
     * 系统管理员(0:否,1:是)
     */
    @TableField(value = "`admin`")
    @Schema(description = "系统管理员(0:否,1:是)")
    private Boolean admin;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 最近一次登录时间
     */
    @TableField(value = "last_login_time")
    @Schema(description = "最近一次登录时间")
    private Date lastLoginTime;

    /**
     * 最近一次修改密码时间
     */
    @TableField(value = "pwd_last_update_time")
    @Schema(description = "最近一次修改密码时间")
    private Date pwdLastUpdateTime;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @Schema(description = "邮箱")
    private String email;

    /**
     * 状态(0:正常,1:禁用)
     */
    @TableField(value = "`status`")
    @Schema(description = "状态(0:正常,1:禁用)")
    private Boolean status;
}