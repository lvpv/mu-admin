package com.lvpb.mu.modules.system.domain.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lvpb.mu.common.domain.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;
import java.util.List;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/8 16:34
 * @description LoginLogPageRequest Description
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginLogPageRequest extends PageRequest {
    @Serial
    private static final long serialVersionUID = 219113949989924859L;

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
    @TableField(value = "user_type")
    private Boolean userType;

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
     * 登陆类型(1:账号登录,2:手机号登录,3:社交登录,4:退出登录)
     */
    @Schema(description = "登陆类型(1:账号登录,2:手机号登录,3:社交登录,4:退出登录)")
    private Integer loginType;

    /**
     * 用户类别(0:成功,1:失败)
     */
    @Schema(description = "用户类别(0:成功,1:失败)")
    private Integer loginResult;

    @Schema(description = "登陆时间")
    private List<Date> createTime;
}
