package com.lvpb.mu.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lvpb.mu.common.code.CommonCode;
import com.lvpb.mu.common.enums.LoginResultEnum;
import com.lvpb.mu.common.enums.LoginTypeEnum;
import com.lvpb.mu.exception.BusinessException;
import com.lvpb.mu.exception.ErrorCode;
import com.lvpb.mu.modules.system.constant.SystemCacheKey;
import com.lvpb.mu.modules.system.convert.TokenConvert;
import com.lvpb.mu.modules.system.convert.UserConvert;
import com.lvpb.mu.modules.system.domain.request.LoginLogRequest;
import com.lvpb.mu.modules.system.domain.request.LoginRequest;
import com.lvpb.mu.modules.system.domain.response.LoginResponse;
import com.lvpb.mu.modules.system.domain.response.TokenResponse;
import com.lvpb.mu.modules.system.domain.response.UserResponse;
import com.lvpb.mu.modules.system.service.AuthService;
import com.lvpb.mu.modules.system.service.LoginLogService;
import com.lvpb.mu.modules.system.service.TokenService;
import com.lvpb.mu.security.AuthUser;
import com.lvpb.mu.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private final RedisTemplate<String, Object> redisTemplate;

    private final LoginLogService loginLogService;


    @Override
    public LoginResponse accountLogin(LoginRequest loginRequest, HttpServletRequest request) {
        // 验证登陆参数
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(username);
        // RequestContextHandler.setRequest(request);
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            LoginLogRequest loginLogRequest = createLoginLog(userResponse, LoginTypeEnum.ACCOUNT, LoginResultEnum.FAIL, "账号或密码为空");
            loginLogService.saveLoginLog(loginLogRequest);
            throw new BusinessException(ErrorCode.REQUEST_PARAMS_IS_NULL);
        }
        if (username.length() > 20) {
            LoginLogRequest loginLogRequest = createLoginLog(userResponse, LoginTypeEnum.ACCOUNT, LoginResultEnum.FAIL, "账号长度超过20个字符");
            loginLogService.saveLoginLog(loginLogRequest);
            throw new BusinessException(ErrorCode.REQUEST_PARAMS_ERROR);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            if (Objects.isNull(authenticate) || Objects.isNull(authenticate.getPrincipal())) {
                throw new BusinessException(ErrorCode.USERNAME_PASSWORD_ERROR);
            }
            AuthUser authUser = (AuthUser) authenticate.getPrincipal();
            TokenResponse token = tokenService.createToken(authUser);
            if (Objects.isNull(token)) {
                LoginLogRequest loginLogRequest = createLoginLog(userResponse, LoginTypeEnum.ACCOUNT, LoginResultEnum.FAIL, "系统生成Token失败");
                loginLogService.saveLoginLog(loginLogRequest);
                throw new BusinessException(CommonCode.SERVE_ERROR);
            }
            // 保存登录用户信息到缓存
            userResponse = UserConvert.INSTANCE.convertResponse(authUser);
            String loginUserCacheKey = SystemCacheKey.LOGIN_USER_CACHE_KEY + token.getUserId();
            redisTemplate.opsForValue().set(loginUserCacheKey, userResponse, token.getTokenExpireSecond(), TimeUnit.SECONDS);
            // 记录登录日志
            LoginLogRequest loginLogRequest = createLoginLog(userResponse, LoginTypeEnum.ACCOUNT, LoginResultEnum.SUCCESS, null);
            loginLogService.saveLoginLog(loginLogRequest);
            SecurityUtils.setLoginUser(authUser, request);
            return TokenConvert.INSTANCE.convertLoginResponse(token);
        } catch (BadCredentialsException e) {
            LoginLogRequest loginLogRequest = createLoginLog(userResponse, LoginTypeEnum.ACCOUNT, LoginResultEnum.FAIL, ErrorCode.USERNAME_PASSWORD_ERROR.getMessage());
            loginLogService.saveLoginLog(loginLogRequest);
            throw new BusinessException(ErrorCode.USERNAME_PASSWORD_ERROR, e);
        } catch (DisabledException e) {
            LoginLogRequest loginLogRequest = createLoginLog(userResponse, LoginTypeEnum.ACCOUNT, LoginResultEnum.FAIL, ErrorCode.USER_IS_DISABLED.getMessage());
            loginLogService.saveLoginLog(loginLogRequest);
            throw new BusinessException(ErrorCode.USER_IS_DISABLED, e);
        }
    }

    private LoginLogRequest createLoginLog(UserResponse userResponse, LoginTypeEnum loginType, LoginResultEnum loginResult, String failReason) {
        LoginLogRequest loginLogRequest = new LoginLogRequest();
        loginLogRequest.setUserId(userResponse.getUserId());
        loginLogRequest.setUsername(userResponse.getUsername());
        loginLogRequest.setRealName(userResponse.getRealName());
        loginLogRequest.setUserType(userResponse.getAdmin());
        loginLogRequest.setLoginType(loginType.getType());
        loginLogRequest.setLoginResult(loginResult.getResult());
        loginLogRequest.setFailReason(failReason);
        return loginLogRequest;
    }
}
