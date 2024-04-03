package com.lvpb.mu.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/3/29 23:42
 * @description PageRequest
 */

@Data
@Schema(description = "分页请求参数")
public class PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3402264430316288797L;

    /**
     * 当前页码
     */
    @Schema(description = "当前页码", example = "1")
    @Min(value = 1, message = "当前页码不能小于1")
    private Long page = 1L;

    /**
     * 每页数据条数
     */
    @Schema(description = "每页数据条数", example = "10")
    @Min(value = 1, message = "每页数据条数不能小于1")
    @Max(value = 200, message = "每页数据条数不能大于200")
    private Long size = 10L;

    @Valid
    @Schema(description = "排序规则集合")
    @Size(max = 10, message = "排序字段最多10个")
    private List<SortItem> sortItems;

    @Data
    @Schema(description = "排序字段")
    public static class SortItem {

        @Schema(description = "排序字段", example = "create_time")
        @NotBlank(message = "排序字段不能为空")
        @Length(max = 30, message = "排序字段长度不能超过30字符")
        private String column;

        @Schema(description = "排序规则(true:升序,false:降序)", example = "true", defaultValue = "true")
        @NotNull(message = "排序规则不能为空")
        private boolean asc = true;
    }
}
