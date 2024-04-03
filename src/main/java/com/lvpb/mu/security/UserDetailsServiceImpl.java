package com.lvpb.mu.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.lvpb.mu.modules.system.domain.convert.UserConvert;
import com.lvpb.mu.modules.system.domain.entity.Menu;
import com.lvpb.mu.modules.system.domain.entity.Role;
import com.lvpb.mu.modules.system.domain.entity.User;
import com.lvpb.mu.modules.system.service.MenuService;
import com.lvpb.mu.modules.system.service.RoleService;
import com.lvpb.mu.modules.system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 22:45
 * @description UserDetailsServiceImpl
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    private final RoleService roleService;

    private final MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StrUtil.isBlank(username)) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        User user = userService.getUserByName(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        AuthUser authUser = UserConvert.INSTANCE.convertAuthUser(user);
        // 获取用户角色
        Set<Role> roles = roleService.getRolesByUserId(authUser.getUserId());
        authUser.setRoles(roles);
        // 获取用户权限
        if (CollectionUtil.isEmpty(roles)) {
            authUser.setPermissions(Collections.emptySet());
        } else {
            Set<Long> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toSet());
            Set<Menu> menus = menuService.getPermissionsByRoleIds(roleIds);
            if (CollUtil.isNotEmpty(menus)) {
                Set<String> permissions = menus.stream().map(Menu::getPermission).collect(Collectors.toSet());
                authUser.setPermissions(permissions);
            }
        }
        return authUser;
    }
}
