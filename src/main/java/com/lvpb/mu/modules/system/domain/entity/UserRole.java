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
 * @description 系统管理-用户角色关系表
 * @version 1.0.0
 */

@Schema(description="系统管理-用户角色关系表")
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "system_user_role")
public class UserRole extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1545644175413580522L;

    /**
     * 用户角色关系编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description="用户角色关系编号")
    private Long id;

    /**
     * 用户编号
     */
    @TableField(value = "user_id")
    @Schema(description="用户编号")
    private Long userId;

    /**
     * 角色编号
     */
    @TableField(value = "role_id")
    @Schema(description="角色编号")
    private Long roleId;
}