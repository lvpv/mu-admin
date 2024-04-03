package com.lvpb.mu.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 20:16
 * @description BaseEntity
 */

@Data
public class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1672542176194181653L;


    @TableField(value = "deleted")
    @TableLogic
    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "创建人")
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Long creator;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @Schema(description = "更新人")
    @TableField(value = "updater", fill = FieldFill.INSERT_UPDATE)
    private Long updater;

    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
