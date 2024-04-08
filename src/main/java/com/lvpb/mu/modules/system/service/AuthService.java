package com.lvpb.mu.modules.system.service;

import com.lvpb.mu.modules.system.domain.request.LoginRequest;
import com.lvpb.mu.modules.system.domain.response.LoginResponse;
import com.lvpb.mu.modules.system.domain.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/7 19:07
 * @description AuthService Description
 */
public interface AuthService {

    LoginResponse accountLogin(LoginRequest loginRequest, HttpServletRequest request);

    UserResponse getUserInfo();

}
