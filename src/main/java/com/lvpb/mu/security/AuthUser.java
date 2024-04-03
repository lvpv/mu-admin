package com.lvpb.mu.security;

import cn.hutool.core.collection.CollUtil;
import com.lvpb.mu.modules.system.domain.entity.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 21:52
 * @description AuthUser
 */

@Data
public class AuthUser implements UserDetails {
    @Serial
    private static final long serialVersionUID = 5442088698547198125L;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别(0:未知,1:男,2:女)
     */
    private Integer gender;

    /**
     * 系统管理员(0:否,1:是)
     */
    private Boolean admin;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 最近一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 最近一次修改密码时间
     */
    private Date pwdLastUpdateTime;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态(0:正常,1:禁用)
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 角色列表
     */
    private Set<Role> roles;

    /**
     * 权限列表
     */
    private Set<String> permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollUtil.isEmpty(permissions)){
            return null;
        }
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !status;
    }
}
