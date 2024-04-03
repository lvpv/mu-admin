package com.lvpb.mu.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lvpb.mu.modules.system.domain.entity.Role;

import java.util.Set;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 20:48
 * @description RoleService
 */
public interface RoleService extends IService<Role> {

    Set<Role> getRolesByUserId(Long userId);
}
