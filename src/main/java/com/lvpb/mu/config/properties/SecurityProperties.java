package com.lvpb.mu.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 18:41
 * @description SecurityProperties Description
 */

@Data
@ConfigurationProperties(prefix = "mu.security")
public class SecurityProperties {


    /**
     * token在Header中的名称
     */
    private String tokenName = "Authorization";

    /**
     * token前缀
     */
    private String tokenPrefix = "Bearer ";

    /**
     * token过期时间，单位：秒，默认2小时
     */
    private Long tokenExpireTime = 7200L;

    /**
     * refreshToken过期时间，单位：秒，默认7天
     */
    private Long refreshTokenExpireTime = 7 * 24 * 60 * 60L;

    /**
     * 密码加密强度
     */
    private int passwordEncoderLength = 4;

    /**
     * 忽略鉴权访问的url列表
     */
    private List<String> ignoreUrls = Collections.emptyList();

}
