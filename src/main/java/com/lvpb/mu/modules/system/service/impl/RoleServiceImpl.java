package com.lvpb.mu.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvpb.mu.modules.system.domain.entity.Role;
import com.lvpb.mu.modules.system.mapper.RoleMapper;
import com.lvpb.mu.modules.system.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 20:48
 * @description RoleServiceImpl
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Set<Role> getRolesByUserId(Long userId) {
        if (Objects.isNull(userId)) {
            return Collections.emptySet();
        }
        return baseMapper.findRolesByUserId(userId);
    }
}
