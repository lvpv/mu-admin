package com.lvpb.mu.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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


    private String tokenName;

    private String tokenPrefix;

    private Long tokenExpireTime;

    private Long refreshTokenExpireTime;

    private List<String> ignoreUrls;

}
