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
 * @date 2024/4/3 20:48
 * @description 系统管理-角色表
 * @version 1.0.0
 */

@Schema(description="系统管理-角色表")
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "system_role")
public class Role extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 5845137120127351849L;
    /**
     * 角色编号
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    @Schema(description="角色编号")
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    @Schema(description="角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField(value = "role_code")
    @Schema(description="角色编码")
    private String roleCode;

    /**
     * 系统内置角色(0:否,1:是)
     */
    @TableField(value = "internal")
    @Schema(description="系统内置角色(0:否,1:是)")
    private Boolean internal;

    /**
     * 角色描述
     */
    @TableField(value = "remark")
    @Schema(description="角色描述")
    private String remark;

    /**
     * 状态(0:正常,1:禁用)
     */
    @TableField(value = "`status`")
    @Schema(description="状态(0:正常,1:禁用)")
    private Boolean status;
}