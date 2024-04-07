package com.lvpb.mu.modules.system.convert;

import com.lvpb.mu.modules.system.domain.entity.LoginLog;
import com.lvpb.mu.modules.system.domain.request.LoginLogRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/7 23:55
 * @description LoginLogConvert
 */
@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    @Mapping(target = "updater", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    LoginLog convert(LoginLogRequest loginLogRequest);
}
