package com.lvpb.mu.config;

import com.lvpb.mu.config.properties.SecurityProperties;
import com.lvpb.mu.constant.MuConstant;
import com.lvpb.mu.security.ThreadLocalSecurityContextHolderStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 18:42
 * @description 认证授权配置
 */


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfiguration {

    private final SecurityProperties securityProperties;

    private final UserDetailsService userDetailsService;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AccessDeniedHandler accessDeniedHandler;

    private final static String[] STATIC_PATH = {
            "/**.html",
            "/**.js",
            "/**.css",
            "/favicon.ico",
            "/webjars/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/druid/**"
    };
    private final static String DRUID_LOGIN_PATH = "/druid/submitLogin";
    private final static String AUTH_LOGIN_PATH = "/system/**";
    private final static String SECURITY_SET_STRATEGY_METHOD_NAME = "setStrategyName";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 禁用csrf和开启cors
        http.csrf(AbstractHttpConfigurer::disable).cors(Customizer.withDefaults())
                // 禁用Session
//                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(c -> c.authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.OPTIONS, MuConstant.ANY_PATH).permitAll()
                .requestMatchers(HttpMethod.GET, STATIC_PATH).permitAll()
                .requestMatchers(HttpMethod.POST, DRUID_LOGIN_PATH).permitAll()
                .requestMatchers(AUTH_LOGIN_PATH).permitAll()
                .requestMatchers(securityProperties.getIgnoreUrls().toArray(new String[0])).permitAll()
                // 放行登录接口
                .anyRequest().authenticated());
        return http.build();
    }

    @Bean
    public MethodInvokingFactoryBean securityContextHolderMethodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod(SECURITY_SET_STRATEGY_METHOD_NAME);
        methodInvokingFactoryBean.setArguments(ThreadLocalSecurityContextHolderStrategy.class.getName());
        return methodInvokingFactoryBean;
    }

}
