package com.lvpb.mu.modules.system.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lvpb.mu.config.properties.SecurityProperties;
import com.lvpb.mu.constant.MuConstant;
import com.lvpb.mu.exception.BusinessException;
import com.lvpb.mu.exception.ErrorCode;
import com.lvpb.mu.modules.system.convert.TokenConvert;
import com.lvpb.mu.modules.system.domain.entity.Token;
import com.lvpb.mu.modules.system.domain.response.TokenResponse;
import com.lvpb.mu.modules.system.mapper.TokenMapper;
import com.lvpb.mu.modules.system.service.TokenService;
import com.lvpb.mu.security.AuthUser;
import com.lvpb.mu.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/7 15:38
 * @description TokenServiceImpl Description
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {


    private final JwtTokenService jwtTokenService;

    private final SecurityProperties securityProperties;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TokenResponse createToken(AuthUser authUser) {
        if (Objects.isNull(authUser)) {
            throw new BusinessException(ErrorCode.REQUEST_PARAMS_ERROR);
        }
        Long userId = authUser.getUserId();
        String username = authUser.getUsername();
        if (Objects.isNull(userId) || StrUtil.isBlank(username)) {
            throw new BusinessException(ErrorCode.REQUEST_PARAMS_ERROR);
        }
        DateTime date = DateUtil.date();
        String accessToken = jwtTokenService.generateAccessToken(date, userId, username);
        String refreshToken = jwtTokenService.generateRefreshToken(date, userId, username);
        DateTime tokenExpire = DateUtil.offsetSecond(date, securityProperties.getTokenExpire());
        DateTime refreshTokenExpire = DateUtil.offsetSecond(date, securityProperties.getRefreshTokenExpire());
        Token userToken = new LambdaQueryChainWrapper<>(baseMapper).eq(Token::getUserId, userId).last(MuConstant.SELECT_ONE_SQL).one();
        Token token = buildToken(authUser, accessToken, tokenExpire, refreshToken, refreshTokenExpire);
        if (Objects.isNull(userToken)) {
            baseMapper.insert(token);
        } else {
            token.setId(userToken.getId());
            baseMapper.updateById(token);
        }
        TokenResponse tokenResponse = TokenConvert.INSTANCE.convertResponse(token);
        tokenResponse.setTokenExpireSecond((long) securityProperties.getTokenExpire());
        tokenResponse.setRefreshTokenExpireSecond((long) securityProperties.getRefreshTokenExpire());
        return tokenResponse;
    }


    private Token buildToken(AuthUser authUser, String accessToken, DateTime tokenExpire, String refreshToken, DateTime refreshTokenExpire) {
        Token token = new Token();
        token.setUserId(authUser.getUserId());
        token.setUsername(authUser.getUsername());
        token.setUserType(authUser.getAdmin());
        token.setRealName(authUser.getRealName());
        token.setAccessToken(accessToken);
        token.setAccessTokenExpires(tokenExpire);
        token.setRefreshToken(refreshToken);
        token.setRefreshTokenExpires(refreshTokenExpire);
        return token;
    }
}
