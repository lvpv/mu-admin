package com.lvpb.mu.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvpb.mu.constant.MuConstant;
import com.lvpb.mu.exception.BusinessException;
import com.lvpb.mu.exception.ErrorCode;
import com.lvpb.mu.modules.system.domain.entity.User;
import com.lvpb.mu.modules.system.mapper.UserMapper;
import com.lvpb.mu.modules.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 20:47
 * @description UserServiceImpl
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUserById(Long id) {
        if (Objects.isNull(id)) {
            log.warn("[getUserById] id is null");
            throw new BusinessException(ErrorCode.REQUEST_PARAMS_IS_NULL);
        }
        if (id <= 0) {
            log.warn("[getUserById] id less than or equal to 0,id:{}", id);
            throw new BusinessException(ErrorCode.REQUEST_PARAMS_ERROR);
        }
        User user = baseMapper.selectById(id);
        if (Objects.isNull(user)) {
            log.warn("[getUserById] user not exist,id:{}", id);
            throw new BusinessException(ErrorCode.REQUEST_DATE_NOT_EXIST);
        }
        return user;
    }

    @Override
    public User getUserByName(String username) {
        if (StrUtil.isBlank(username)) {
            return null;
        }
        return new LambdaQueryChainWrapper<>(baseMapper)
                .eq(User::getUsername, username)
                .last(MuConstant.SELECT_ONE_SQL).one();
    }
}
