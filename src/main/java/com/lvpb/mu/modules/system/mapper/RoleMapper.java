package com.lvpb.mu.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lvpb.mu.modules.system.domain.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 20:48
 * @description RoleMapper
 */
public interface RoleMapper extends BaseMapper<Role> {
    Set<Role> findRolesByUserId(@Param("userId") Long userId);
}