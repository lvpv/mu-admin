package com.lvpb.mu.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lvpb.mu.modules.system.domain.entity.Token;
import com.lvpb.mu.security.AuthUser;

/**
 * @author lvpb
 * @date 2024/4/7 15:38
 * @version 1.0.0
 * @description TokenService Description
 */
public interface TokenService extends IService<Token>{

    Token createToken(AuthUser authUser);
}
