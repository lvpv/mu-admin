package com.lvpb.mu.modules.system.service;

import com.lvpb.mu.modules.system.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 20:47
 * @description UserService
 */
public interface UserService extends IService<User> {

    User getUserById(Long id);

    User getUserByName(String username);
}
