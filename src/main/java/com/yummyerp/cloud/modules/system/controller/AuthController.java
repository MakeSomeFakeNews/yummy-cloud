package com.yummyerp.cloud.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.yummyerp.cloud.annotation.Log;
import com.yummyerp.cloud.constant.LogConst;
import com.yummyerp.cloud.modules.common.result.Result;
import com.yummyerp.cloud.modules.system.dto.*;
import com.yummyerp.cloud.modules.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户认证")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/captcha")
    @ApiOperation("获取验证码")
    @Log(title = "获取验证码", businessType = LogConst.BusinessType.OTHER)
    public Result<CaptchaResponse> getCaptcha() {
        return Result.success(userService.getCaptcha());
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    @Log(title = "用户登录", businessType = LogConst.BusinessType.OTHER)
    public Result<LoginResponse> login(@Validated @RequestBody LoginRequest request) {
        return Result.success(userService.login(request));
    }

    @PostMapping("/logout")
    @ApiOperation("用户登出")
    @SaCheckLogin
    @Log(title = "用户登出", businessType = LogConst.BusinessType.OTHER)
    public Result<Void> logout() {
        userService.logout();
        return Result.success();
    }

    @GetMapping("/getUserInfo")
    @ApiOperation("获取用户信息")
    @SaCheckLogin
    @Log(title = "获取用户信息", businessType = LogConst.BusinessType.OTHER)
    public Result<UserInfoResponse> getUserInfo() {
        return Result.success(userService.getUserInfo());
    }

    @GetMapping("/getUserRoutes")
    @ApiOperation("获取用户路由")
    @SaCheckLogin
    @Log(title = "获取用户路由", businessType = LogConst.BusinessType.OTHER)
    public Result<List<MenuTreeResponse>> getUserRoutes() {
        return Result.success(userService.getUserRoutes());
    }

    @PostMapping("/changePassword")
    @ApiOperation("修改密码")
    @SaCheckLogin
    @Log(title = "修改密码", businessType = LogConst.BusinessType.UPDATE)
    public Result<Void> changePassword(@Validated @RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return Result.success();
    }
}