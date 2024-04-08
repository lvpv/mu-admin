package com.lvpb.mu.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lvpb.mu.modules.system.domain.entity.LoginLog;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/5 01:01
 * @description LoginLogService
 */
public interface LoginLogService extends IService<LoginLog> {

    void saveLoginLog(LoginLog loginLog);

}
