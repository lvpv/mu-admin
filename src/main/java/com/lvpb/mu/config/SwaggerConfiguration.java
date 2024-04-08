package com.lvpb.mu.config;

import com.lvpb.mu.config.properties.SecurityProperties;
import com.lvpb.mu.config.properties.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 18:00
 * @description 接口文档配置
 */
@Configuration
@EnableConfigurationProperties({SwaggerProperties.class})
@ConditionalOnProperty(prefix = "springdoc.api-docs", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfiguration {

    @Bean
    public OpenAPI createApi(SwaggerProperties properties, SecurityProperties securityProperties) {
        Map<String, SecurityScheme> schemes = buildSecuritySchemes(securityProperties);
        OpenAPI openAPI = new OpenAPI()
                // 接口信息
                .info(buildInfo(properties))
                // 接口安全配置
                .components(new Components().securitySchemes(schemes))
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
        schemes.keySet().forEach(key -> openAPI.addSecurityItem(new SecurityRequirement().addList(key)));
         return openAPI;
    }

    private Info buildInfo(SwaggerProperties properties) {
        return new Info()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .contact(new Contact().name(properties.getAuthor()).email(properties.getEmail()));
    }


    @Bean
    public GroupedOpenApi systemGroupOpenApi(SecurityProperties securityProperties) {
        return GroupedOpenApi.builder()
                .group("system")
                .displayName("系统管理")
                .pathsToMatch("/system/**")
                .packagesToScan("com.lvpb.mu.modules.system")
                .addOperationCustomizer((operation, handlerMethod) -> operation
                        .addParametersItem(buildSecurityHeaderParameter(securityProperties)))
                .build();
    }

    //  添加多个认证参数
    private Map<String, SecurityScheme> buildSecuritySchemes(SecurityProperties securityProperties) {
        Map<String, SecurityScheme> securitySchemes = new HashMap<>();
        SecurityScheme securityScheme = new SecurityScheme()
                // 类型
                .type(SecurityScheme.Type.APIKEY)
                // 请求头的 name
                .name(securityProperties.getTokenName())
                // token 所在位置
                .in(SecurityScheme.In.HEADER);
        securitySchemes.put(HttpHeaders.AUTHORIZATION, securityScheme);
        return securitySchemes;
    }

    private static Parameter buildSecurityHeaderParameter(SecurityProperties securityProperties) {
        return new Parameter()
                .name(securityProperties.getTokenName())
                .description("认证Token")
                .in(String.valueOf(SecurityScheme.In.HEADER))
                .schema(new StringSchema()._default("Bearer test1").name(securityProperties.getTokenName()).description("认证 Token"));
    }
}
