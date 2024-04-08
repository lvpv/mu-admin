package com.lvpb.mu.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvpb.mu.constant.MuConstant;
import com.lvpb.mu.exception.BusinessException;
import com.lvpb.mu.exception.ErrorCode;
import com.lvpb.mu.handler.RequestContextHandler;
import com.lvpb.mu.modules.system.domain.entity.LoginLog;
import com.lvpb.mu.modules.system.domain.request.LoginLogPageRequest;
import com.lvpb.mu.modules.system.mapper.LoginLogMapper;
import com.lvpb.mu.modules.system.service.LoginLogService;
import com.lvpb.mu.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Async(MuConstant.THREAD_POOL_BEAN_NAME)
    @Transactional(rollbackFor = Exception.class)
    public void saveLoginLog(LoginLog loginLog) {
        if (Objects.isNull(loginLog)) {
            throw new BusinessException(ErrorCode.REQUEST_PARAMS_IS_NULL);
        }
        baseMapper.insert(loginLog);
        RequestContextHandler.clear();
    }

    @Override
    public Page<LoginLog> getLoginLogByPage(LoginLogPageRequest pageRequest) {
        Page<LoginLog> page = PageUtils.buildPage(pageRequest);
        List<Date> createTimes = Optional.ofNullable(pageRequest.getCreateTime()).orElse(Collections.emptyList());
        boolean condition = CollUtil.isNotEmpty(createTimes) && createTimes.size() == 2;
        LambdaQueryChainWrapper<LoginLog> wrapper = new LambdaQueryChainWrapper<>(baseMapper)
                .like(StrUtil.isNotBlank(pageRequest.getUsername()), LoginLog::getUsername, pageRequest.getUsername())
                .like(StrUtil.isNotBlank(pageRequest.getRealName()), LoginLog::getRealName, pageRequest.getRealName())
                .like(StrUtil.isNotBlank(pageRequest.getLoginIp()), LoginLog::getLoginIp, pageRequest.getLoginIp())
                .like(StrUtil.isNotBlank(pageRequest.getLoginRegion()), LoginLog::getLoginRegion, pageRequest.getLoginRegion())
                .eq(Objects.nonNull(pageRequest.getUserType()), LoginLog::getUserType, pageRequest.getUserType())
                .eq(Objects.nonNull(pageRequest.getLoginType()), LoginLog::getLoginType, pageRequest.getLoginType())
                .eq(Objects.nonNull(pageRequest.getLoginType()), LoginLog::getLoginType, pageRequest.getLoginType());
        if (condition) {
            wrapper.between(LoginLog::getCreateTime, createTimes.get(0), createTimes.get(1));
        }
        return wrapper.page(page);
    }
}
