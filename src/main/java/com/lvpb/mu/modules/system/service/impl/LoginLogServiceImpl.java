package com.lvpb.mu.modules.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvpb.mu.modules.system.domain.entity.LoginLog;
import com.lvpb.mu.modules.system.mapper.LoginLogMapper;
import com.lvpb.mu.modules.system.service.LoginLogService;
/**
 * @author lvpb
 * @date 2024/4/5 01:01
 * @description LoginLogServiceImpl
 * @version 1.0.0
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService{

}
