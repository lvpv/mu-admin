package com.lvpb.mu.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvpb.mu.common.code.CommonCode;
import com.lvpb.mu.exception.BusinessException;
import com.lvpb.mu.exception.ErrorCode;
import com.lvpb.mu.handler.RequestContextHandler;
import com.lvpb.mu.modules.system.convert.LoginLogConvert;
import com.lvpb.mu.modules.system.domain.entity.LoginLog;
import com.lvpb.mu.modules.system.domain.request.LoginLogRequest;
import com.lvpb.mu.modules.system.mapper.LoginLogMapper;
import com.lvpb.mu.modules.system.service.LoginLogService;
import com.lvpb.mu.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/5 01:01
 * @description LoginLogServiceImpl
 */
@Slf4j
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Override
    //@Async(MuConstant.THREAD_POOL_BEAN_NAME)
    @Transactional(rollbackFor = Exception.class)
    public void saveLoginLog(LoginLogRequest loginLogRequest) {
        if (Objects.isNull(loginLogRequest)) {
            throw new BusinessException(ErrorCode.REQUEST_PARAMS_IS_NULL);
        }
        log.info("当前线程名称:{}", Thread.currentThread().getName());
        log.info("Saving login log: {}", loginLogRequest);
        HttpServletRequest request = RequestContextHandler.getRequest();
        if (Objects.isNull(request)) {
            throw new BusinessException(CommonCode.SERVE_ERROR);
        }
        String ip = IpUtils.getIpAddr(request);
        String address = IpUtils.getAddressByIp(ip);
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        loginLogRequest.setLoginIp(ip);
        loginLogRequest.setLoginRegion(address);
        loginLogRequest.setUserAgent(userAgent);
        LoginLog loginLog = LoginLogConvert.INSTANCE.convert(loginLogRequest);
        baseMapper.insert(loginLog);
        RequestContextHandler.clear();
    }
}
