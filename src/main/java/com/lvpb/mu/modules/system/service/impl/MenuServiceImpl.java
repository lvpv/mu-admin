package com.lvpb.mu.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvpb.mu.modules.system.domain.entity.Menu;
import com.lvpb.mu.modules.system.mapper.MenuMapper;
import com.lvpb.mu.modules.system.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 20:48
 * @description MenuServiceImpl
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public Set<Menu> getPermissionsByRoleIds(Set<Long> roleIds) {
        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptySet();
        }
        return baseMapper.findMenusByRoleIds(roleIds);
    }
}
