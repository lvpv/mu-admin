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
 * @description 系统管理-菜单表
 * @version 1.0.0
 */

@Schema(description="系统管理-菜单表")
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "system_menu")
public class Menu extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 5333473092866019824L;
    /**
     * 菜单编号
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    @Schema(description="菜单编号")
    private Long menuId;

    /**
     * 菜单名称
     */
    @TableField(value = "`name`")
    @Schema(description="菜单名称")
    private String name;

    /**
     * 类型
     */
    @TableField(value = "`type`")
    @Schema(description="类型")
    private Integer type;

    /**
     * 父菜单ID
     */
    @TableField(value = "parent_id")
    @Schema(description="父菜单ID")
    private Long parentId;

    /**
     * 显示顺序
     */
    @TableField(value = "sort")
    @Schema(description="显示顺序")
    private Integer sort;

    /**
     * 路由地址
     */
    @TableField(value = "`path`")
    @Schema(description="路由地址")
    private String path;

    /**
     * 菜单图标
     */
    @TableField(value = "icon")
    @Schema(description="菜单图标")
    private String icon;

    /**
     * 组件路径
     */
    @TableField(value = "component")
    @Schema(description="组件路径")
    private String component;

    /**
     * 权限标识
     */
    @TableField(value = "permission")
    @Schema(description="权限标识")
    private String permission;

    /**
     * 是否为外链(0:否,1:是)
     */
    @TableField(value = "frame")
    @Schema(description="是否为外链(0:否,1:是)")
    private Boolean frame;

    /**
     * 外链地址
     */
    @TableField(value = "frame_url")
    @Schema(description="外链地址")
    private String frameUrl;

    /**
     * 是否缓存(0:不缓存,1:缓存)
     */
    @TableField(value = "keep_alive")
    @Schema(description="是否缓存(0:不缓存,1:缓存)")
    private Boolean keepAlive;

    /**
     * 显示状态(0:隐藏,1:显示)
     */
    @TableField(value = "hide")
    @Schema(description="显示状态(0:隐藏,1:显示)")
    private Boolean hide;

    /**
     * 是否总是显示(0:否,1:是)
     */
    @TableField(value = "always_show")
    @Schema(description="是否总是显示(0:否,1:是)")
    private Boolean alwaysShow;

    /**
     * 状态(0:正常,1:禁用)
     */
    @TableField(value = "`status`")
    @Schema(description="状态(0:正常,1:禁用)")
    private Boolean status;
}