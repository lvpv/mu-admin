package com.lvpb.mu.modules.system.controller;

import com.lvpb.mu.common.domain.Result;
import com.lvpb.mu.modules.system.domain.convert.UserConvert;
import com.lvpb.mu.modules.system.domain.entity.User;
import com.lvpb.mu.modules.system.domain.response.UserResponse;
import com.lvpb.mu.modules.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lvpb
 * @version 1.0.0
 * @date 2024/4/3 20:52
 * @description UserController
 */
@Tag(name = "用户管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "根据ID获取用户信息")
    @Parameter(name = "id", description = "用户编号", required = true, in = ParameterIn.QUERY, example = "1")
    @GetMapping("/id")
    public Result<UserResponse> getUserById(@RequestParam("id") Long id){
        User user = userService.getUserById(id);
        UserResponse response = UserConvert.INSTANCE.convertResponse(user);
        return Result.success(response);
    }
}
