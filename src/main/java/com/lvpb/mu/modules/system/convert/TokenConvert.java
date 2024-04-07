package com.lvpb.mu.modules.system.convert;

import com.lvpb.mu.modules.system.domain.entity.Token;
import com.lvpb.mu.modules.system.domain.response.LoginResponse;
import com.lvpb.mu.modules.system.domain.response.TokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/7 23:28
 * @description TokenConvert
 */
@Mapper
public interface TokenConvert {

    TokenConvert INSTANCE = Mappers.getMapper(TokenConvert.class);


    @Mapping(target = "tokenExpireSecond", ignore = true)
    @Mapping(target = "refreshTokenExpireSecond", ignore = true)
    TokenResponse convertResponse(Token token);

    LoginResponse convertLoginResponse(TokenResponse tokenResponse);
}
