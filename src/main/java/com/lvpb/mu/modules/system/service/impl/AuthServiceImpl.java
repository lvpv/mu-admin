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
import com.lvpb.mu.modules.system.domain.entity.LoginLog;
import com.lvpb.mu.modules.system.domain.request.LoginRequest;
import com.lvpb.mu.modules.system.domain.response.LoginResponse;
import com.lvpb.mu.modules.system.domain.response.TokenResponse;
import com.lvpb.mu.modules.system.domain.response.UserResponse;
import com.lvpb.mu.modules.system.service.AuthService;
import com.lvpb.mu.modules.system.service.LoginLogService;
import com.lvpb.mu.modules.system.service.TokenService;
import com.lvpb.mu.security.AuthUser;
import com.lvpb.mu.security.SecurityUtils;
import com.lvpb.mu.utils.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
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

        String ip = IpUtils.getIpAddr(request);
        String address = IpUtils.getAddressByIp(ip);
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(username);
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            log.info("[accountLogin]");
            createLoginLog(userResponse, ip, address, userAgent, LoginTypeEnum.ACCOUNT, LoginResultEnum.FAIL, "账号或密码为空");
            throw new BusinessException(ErrorCode.REQUEST_PARAMS_IS_NULL);
        }
        if (username.length() > 20) {
            createLoginLog(userResponse, ip, address, userAgent, LoginTypeEnum.ACCOUNT, LoginResultEnum.FAIL, "账号长度超过20个字符");
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
                createLoginLog(userResponse, ip, address, userAgent, LoginTypeEnum.ACCOUNT, LoginResultEnum.FAIL, "系统生成Token失败");
                throw new BusinessException(CommonCode.SERVE_ERROR);
            }
            // 保存登录用户信息到缓存
            userResponse = UserConvert.INSTANCE.convertResponse(authUser);
            String loginUserCacheKey = SystemCacheKey.LOGIN_USER_CACHE_KEY.formatted(authUser.getUserId());
            redisTemplate.opsForValue().set(loginUserCacheKey, authUser, token.getTokenExpireSecond(), TimeUnit.SECONDS);
            // 记录登录日志
            createLoginLog(userResponse, ip, address, userAgent, LoginTypeEnum.ACCOUNT, LoginResultEnum.SUCCESS, "登陆成功");
            SecurityUtils.setLoginUser(authUser, request);
            return TokenConvert.INSTANCE.convertLoginResponse(token);
        } catch (BadCredentialsException e) {
            createLoginLog(userResponse, ip, address, userAgent, LoginTypeEnum.ACCOUNT, LoginResultEnum.FAIL, "用户名或密码错误");
            throw new BusinessException(ErrorCode.USERNAME_PASSWORD_ERROR, e);
        } catch (DisabledException e) {
            createLoginLog(userResponse, ip, address, userAgent, LoginTypeEnum.ACCOUNT, LoginResultEnum.FAIL, "账号已被禁用");
            throw new BusinessException(ErrorCode.USER_IS_DISABLED, e);
        }
    }

    @Override
    public UserResponse getUserInfo() {
        AuthUser loginUser = SecurityUtils.getLoginUser();
        return UserConvert.INSTANCE.convertResponse(loginUser);
    }

    private void createLoginLog(UserResponse userResponse, String ip, String address, String userAgent, LoginTypeEnum loginType, LoginResultEnum loginResult, String failReason) {
        LoginLog loginLog = new LoginLog();
        loginLog.setLoginIp(ip);
        loginLog.setLoginRegion(address);
        loginLog.setUserAgent(userAgent);
        loginLog.setUserId(userResponse.getUserId());
        loginLog.setUsername(userResponse.getUsername());
        loginLog.setRealName(userResponse.getRealName());
        loginLog.setUserType(userResponse.getAdmin());
        loginLog.setLoginType(loginType.getType());
        loginLog.setLoginResult(loginResult.getResult());
        loginLog.setFailReason(failReason);
        loginLog.setCreator(userResponse.getUserId());
        loginLog.setUpdater(userResponse.getUserId());
        loginLogService.saveLoginLog(loginLog);
    }
}
