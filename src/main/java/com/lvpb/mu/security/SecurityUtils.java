package com.lvpb.mu.security;

import cn.hutool.core.collection.CollUtil;
import com.lvpb.mu.modules.system.domain.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.util.*;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 21:51
 * @description SecurityUtils
 */
public class SecurityUtils {

    /**
     * 获取当前登录用户信息
     *
     * @return 登录用户信息
     */
    public static AuthUser getLoginUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        if (Objects.isNull(principal) || !(principal instanceof AuthUser)) {
            return null;
        }
        return (AuthUser) principal;
    }

    /**
     * 获取当前登录用户编号
     *
     * @return 用户编号
     */
    public static Long getLoginUserId() {
        return Optional.ofNullable(getLoginUser()).isPresent() ? getLoginUser().getUserId() : null;
    }

    /**
     * 获取当前登录用户username
     *
     * @return username
     */
    public static String getLoginUsername() {
        return Optional.ofNullable(getLoginUser()).isPresent() ? getLoginUser().getUsername() : null;
    }

    /**
     * 获取当前登录用户真实姓名
     *
     * @return 真实姓名
     */
    public static String getLoginUserRealName() {
        return Optional.ofNullable(getLoginUser()).isPresent() ? getLoginUser().getRealName() : null;
    }

    /**
     * 获取当前登录用户是否为系统管理员
     *
     * @return 是否为管理员
     */
    public static boolean isAdmin() {
        return Optional.ofNullable(getLoginUser()).isPresent() ? getLoginUser().getAdmin() : false;
    }


    /**
     * 获取当前登录用户角色列表
     *
     * @return 角色列表
     */
    public static Set<Role> getLoginUserRoles() {
        Set<Role> roles = Optional.ofNullable(getLoginUser()).isPresent() ? getLoginUser().getRoles() : null;
        if (CollUtil.isEmpty(roles)) {
            return new HashSet<>(0);
        }
        return roles;
    }

    /**
     * 获取当前登录用户权限列表
     *
     * @return 权限列表
     */
    public static Set<String> getLoginUserPermissions() {
        Set<String> roles = Optional.ofNullable(getLoginUser()).isPresent() ? getLoginUser().getPermissions() : null;
        if (CollUtil.isEmpty(roles)) {
            return new HashSet<>(0);
        }
        return roles;
    }

    public static void setLoginUser(AuthUser loginUser, HttpServletRequest request) {
        // 创建 Authentication，并设置到上下文
        Authentication authentication = buildAuthentication(loginUser, request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 额外设置到 request 中，用于 ApiAccessLogFilter 可以获取到用户编号；
        // 原因是，Spring Security 的 Filter 在 ApiAccessLogFilter 后面，在它记录访问日志时，线上上下文已经没有用户编号等信息
        // WebFrameworkUtils.setLoginUserId(request, loginUser.getId());
        // WebFrameworkUtils.setLoginUserType(request, loginUser.getUserType());
    }

    private static Authentication buildAuthentication(AuthUser loginUser, HttpServletRequest request) {
        // 创建 UsernamePasswordAuthenticationToken 对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser, null, Collections.emptyList());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }
}
