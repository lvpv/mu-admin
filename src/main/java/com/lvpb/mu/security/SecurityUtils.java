package com.lvpb.mu.security;

import cn.hutool.core.collection.CollUtil;
import com.lvpb.mu.exception.BusinessException;
import com.lvpb.mu.exception.ErrorCode;
import com.lvpb.mu.modules.system.domain.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        return (AuthUser) principal;
    }

    /**
     * 获取当前登录用户编号
     *
     * @return 用户编号
     */
    public static Long getLoginUserId() {
        return getLoginUser().getUserId();
    }

    /**
     * 获取当前登录用户username
     *
     * @return username
     */
    public static String getLoginUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 获取当前登录用户真实姓名
     *
     * @return 真实姓名
     */
    public static String getLoginUserRealName() {
        return getLoginUser().getRealName();
    }

    /**
     * 获取当前登录用户是否为系统管理员
     *
     * @return 是否为管理员
     */
    public static boolean isAdmin() {
        return getLoginUser().getAdmin();
    }


    /**
     * 获取当前登录用户角色列表
     *
     * @return 角色列表
     */
    public static Set<Role> getLoginUserRoles() {
        Set<Role> roles = getLoginUser().getRoles();
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
        Set<String> roles = getLoginUser().getPermissions();
        if (CollUtil.isEmpty(roles)) {
            return new HashSet<>(0);
        }
        return roles;
    }
}
