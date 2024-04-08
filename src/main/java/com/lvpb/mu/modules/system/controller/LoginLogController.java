package com.lvpb.mu.modules.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lvpb.mu.common.domain.PageResult;
import com.lvpb.mu.common.domain.Result;
import com.lvpb.mu.modules.system.convert.LoginLogConvert;
import com.lvpb.mu.modules.system.domain.entity.LoginLog;
import com.lvpb.mu.modules.system.domain.request.LoginLogPageRequest;
import com.lvpb.mu.modules.system.domain.response.LoginLogResponse;
import com.lvpb.mu.modules.system.service.LoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/8 16:30
 * @description LoginLogController Description
 */
@Tag(name = "登录日志管理")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/login/log")
public class LoginLogController {


    private final LoginLogService loginLogService;


    @PostMapping("/page")
    @Operation(summary = "分页查询登录日志")
    public Result<PageResult<LoginLogResponse>> getLoginLogByPage(@RequestBody LoginLogPageRequest pageRequest) {
        Page<LoginLog> page = loginLogService.getLoginLogByPage(pageRequest);
        PageResult<LoginLogResponse> resultPage = LoginLogConvert.INSTANCE.convertPage(page);
        return Result.success(resultPage);
    }
}
