package com.lvpb.mu.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/3/29 23:27
 * @description PageResult
 */

@Data
public class PageResult<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -6012776381586769918L;

    /**
     * 总数据条数
     */
    @Schema(description = "总数据条数")
    private Long total;

    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private Long pages;

    /**
     * 当前页码
     */
    @Schema(description = "当前页码")
    private Long current;

    /**
     * 每页数据条数
     */
    @Schema(description = "每页数据条数")
    private Long size;

    /**
     * 当前页的数据列表
     */
    @Schema(description = "当前页的数据列表")
    private List<T> records;
}
