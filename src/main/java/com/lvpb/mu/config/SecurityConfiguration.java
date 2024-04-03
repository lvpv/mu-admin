package com.lvpb.mu.config;

import com.lvpb.mu.config.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 18:42
 * @description SecurityConfiguration Description
 */


@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfiguration {
}
