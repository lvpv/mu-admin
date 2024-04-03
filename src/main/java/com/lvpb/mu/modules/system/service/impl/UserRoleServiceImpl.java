package com.lvpb.mu.modules.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvpb.mu.modules.system.mapper.UserRoleMapper;
import com.lvpb.mu.modules.system.domain.entity.UserRole;
import com.lvpb.mu.modules.system.service.UserRoleService;
/**
 * @author lvpb
 * @date 2024/4/3 20:48
 * @description UserRoleServiceImpl
 * @version 1.0.0
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService{

}
