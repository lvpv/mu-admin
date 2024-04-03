package com.lvpb.mu.modules.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lvpb.mu.modules.system.domain.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 20:48
 * @description MenuMapper
 */
public interface MenuMapper extends BaseMapper<Menu> {
    Set<Menu> findMenusByRoleIds(@Param("roleIds") Set<Long> roleIds);
}