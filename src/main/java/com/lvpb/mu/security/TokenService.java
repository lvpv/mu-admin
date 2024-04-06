package com.lvpb.mu.security;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.lvpb.mu.config.properties.SecurityProperties;
import com.lvpb.mu.exception.BusinessException;
import com.lvpb.mu.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/5 01:10
 * @description TokenUtils
 */

@Component
@AllArgsConstructor
public class TokenService {

    private final SecurityProperties securityProperties;


    @SneakyThrows
    public String parseToken(String token) {
        try {
            String username = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody().get("username", String.class);
            if (StrUtil.isNotBlank(username)) {
                return username;
            }
            throw new BusinessException(ErrorCode.TOKEN_VALIDATE_FAIL);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCode.TOKEN_IS_EXPIRED);
        } catch (SignatureException e) {
            throw new BusinessException(ErrorCode.TOKEN_VALIDATE_FAIL);
        }

    }

    public String generateAccessToken(Long userId, String username) {
        return generateToken(userId, username, securityProperties.getTokenExpire());
    }

    public String generateRefreshToken(Long userId, String username) {
        return generateToken(userId, username, securityProperties.getRefreshTokenExpire());
    }


    private String generateToken(Long userId, String username, int expire) {
        DateTime now = DateUtil.date();
        DateTime expireTime = DateUtil.offsetSecond(now, expire);
        return Jwts.builder()
                .setSubject("Authentication")
                .claim("userId", userId)
                .claim("username", username)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        return SecureUtil.aes(securityProperties.getTokenSecret().getBytes()).getSecretKey();
    }
}
