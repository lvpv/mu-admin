package com.lvpb.mu.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lvpb.mu.config.properties.SecurityProperties;
import com.lvpb.mu.exception.BusinessException;
import com.lvpb.mu.exception.ErrorCode;
import com.lvpb.mu.modules.system.domain.entity.Token;
import com.lvpb.mu.modules.system.domain.request.LoginRequest;
import com.lvpb.mu.modules.system.service.AuthService;
import com.lvpb.mu.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/7 19:07
 * @description AuthServiceImpl Description
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SecurityProperties securityProperties;


    @Override
    public Token accountLogin(LoginRequest loginRequest, HttpServletRequest request) {
        String ipAddr = IpUtils.getIpAddr(request);
        String addressByIp = IpUtils.getAddressByIp(ipAddr);
        // 验证登陆参数
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            throw new BusinessException(ErrorCode.REQUEST_PARAMS_IS_NULL);
        }
        if (username.length() > 20){
            throw new BusinessException(ErrorCode.REQUEST_PARAMS_ERROR);
        }
        return null;
    }
}
