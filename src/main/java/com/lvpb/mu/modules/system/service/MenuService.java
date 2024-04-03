package com.lvpb.mu.modules.system.service;

import com.lvpb.mu.modules.system.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 20:48
 * @description MenuService
 */
public interface MenuService extends IService<Menu> {


    Set<Menu> getPermissionsByRoleIds(Set<Long> roleIds);
}
