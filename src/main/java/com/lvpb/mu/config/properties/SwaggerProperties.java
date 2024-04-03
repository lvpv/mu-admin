package com.lvpb.mu.config.properties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 18:06
 * @description SwaggerProperties Description
 */

@Data
@ConfigurationProperties(prefix = "mu.swagger")
public class SwaggerProperties {

    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空")
    private String title;
    /**
     * 描述
     */
    @NotEmpty(message = "描述不能为空")
    private String description;
    /**
     * 作者
     */
    @NotEmpty(message = "作者不能为空")
    private String author;
    /**
     * 版本
     */
    @NotEmpty(message = "版本不能为空")
    private String version;
    /**
     * url
     */
    @NotEmpty(message = "扫描的 package 不能为空")
    private String url;
    /**
     * email
     */
    @NotEmpty(message = "扫描的 email 不能为空")
    private String email;
}
