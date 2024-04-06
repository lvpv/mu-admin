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

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/5 01:01
 * @description 系统管理-登录日志表
 */

@Schema(description = "系统管理-登录日志表")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "system_login_log")
public class LoginLog extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 3231637997502672944L;

    /**
     * 登录日志编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "登录日志编号")
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
     * 真实姓名
     */
    @TableField(value = "real_name")
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 用户类别(0:普通用户,1:管理员)
     */
    @TableField(value = "user_type")
    @Schema(description = "用户类别(0:普通用户,1:管理员)")
    private Boolean userType;

    /**
     * 登陆类型(1:账号登录,2:手机号登录,3:社交登录,4:退出登录)
     */
    @TableField(value = "login_type")
    @Schema(description = "登陆类型(1:账号登录,2:手机号登录,3:社交登录,4:退出登录)")
    private Integer loginType;

    /**
     * 登录IP
     */
    @TableField(value = "login_ip")
    @Schema(description = "登录IP")
    private String loginIp;

    /**
     * 登录地区
     */
    @TableField(value = "login_region")
    @Schema(description = "登录地区")
    private String loginRegion;

    /**
     * user-agent信息
     */
    @TableField(value = "user_agent")
    @Schema(description = "user-agent信息")
    private String userAgent;

    /**
     * 用户类别(0:成功,1:失败)
     */
    @TableField(value = "login_result")
    @Schema(description = "用户类别(0:成功,1:失败)")
    private Boolean loginResult;

    /**
     * 失败原因
     */
    @TableField(value = "fail_reason")
    @Schema(description = "失败原因")
    private String failReason;
}