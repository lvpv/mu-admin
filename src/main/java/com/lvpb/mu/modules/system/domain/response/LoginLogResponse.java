package com.lvpb.mu.modules.system.domain.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/8 16:32
 * @description LoginLogResponse Description
 */
@Data
public class LoginLogResponse {

    /**
     * 登录日志编号
     */
    @Schema(description = "登录日志编号")
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
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    private String realName;

    /**
     * 用户类别(0:普通用户,1:管理员)
     */
    @Schema(description = "用户类别(0:普通用户,1:管理员)")
    private Boolean userType;

    /**
     * 登陆类型(1:账号登录,2:手机号登录,3:社交登录,4:退出登录)
     */
    @Schema(description = "登陆类型(1:账号登录,2:手机号登录,3:社交登录,4:退出登录)")
    private Integer loginType;

    /**
     * 登录IP
     */
    @Schema(description = "登录IP")
    private String loginIp;

    /**
     * 登录地区
     */
    @Schema(description = "登录地区")
    private String loginRegion;

    /**
     * user-agent信息
     */
    @Schema(description = "user-agent信息")
    private String userAgent;

    /**
     * 用户类别(0:成功,1:失败)
     */
    @Schema(description = "用户类别(0:成功,1:失败)")
    private Integer loginResult;

    /**
     * 失败原因
     */
    @Schema(description = "失败原因")
    private String failReason;

    @Schema(description = "登陆时间")
    private Date createTime;
}
