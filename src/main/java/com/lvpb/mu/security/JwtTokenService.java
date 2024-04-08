package com.lvpb.mu.security;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.lvpb.mu.config.properties.SecurityProperties;
import com.lvpb.mu.constant.MuConstant;
import com.lvpb.mu.exception.BusinessException;
import com.lvpb.mu.exception.ErrorCode;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/5 01:10
 * @description TokenUtils
 */

@Component
@AllArgsConstructor
public class JwtTokenService {

    private final SecurityProperties securityProperties;


    public String getAccessToken(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }
        String tokenHeader = request.getHeader(securityProperties.getTokenName());
        if (StrUtil.isBlank(tokenHeader)) {
            return null;
        }
        if (!tokenHeader.startsWith(securityProperties.getTokenPrefix())) {
            return null;
        }
        return tokenHeader.substring(securityProperties.getTokenPrefix().length());
    }

    public String generateAccessToken(Date date, Long userId, String username) {
        return generateToken(date, userId, username, securityProperties.getTokenExpire());
    }

    public String generateRefreshToken(Date date, Long userId, String username) {
        return generateToken(date, userId, username, securityProperties.getRefreshTokenExpire());
    }

    @SneakyThrows
    public Long getUserId(String token) {
        try {
            return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody().get(MuConstant.USER_ID_KEY, Long.class);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCode.TOKEN_IS_EXPIRED);
        } catch (SignatureException e) {
            throw new BusinessException(ErrorCode.TOKEN_VALIDATE_FAIL);
        }

    }


    private String generateToken(Date date, Long userId, String username, int expire) {
        DateTime expireTime = DateUtil.offsetSecond(date, expire);
        Map<String, Object> claims = new HashMap<>();
        claims.put(MuConstant.USER_ID_KEY, userId);
        claims.put(MuConstant.USERNAME_KEY, username);
        return Jwts.builder()
                .setSubject(securityProperties.getTokenName())
                .addClaims(claims)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        return SecureUtil.aes(securityProperties.getTokenSecret().getBytes()).getSecretKey();
    }
}
