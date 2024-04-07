package com.lvpb.mu.modules.system.convert;

import com.lvpb.mu.modules.system.domain.entity.User;
import com.lvpb.mu.modules.system.domain.response.UserResponse;
import com.lvpb.mu.security.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 22:05
 * @description UserConvert
 */

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserResponse convertResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    AuthUser convertAuthUser(User user);
}
