package com.yummyerp.cloud.modules.system.service;

import com.yummyerp.cloud.modules.system.dto.*;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 获取验证码
     */
    CaptchaResponse getCaptcha();

    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);

    /**
     * 用户登出
     */
    void logout();

    /**
     * 获取用户信息
     */
    UserInfoResponse getUserInfo();

    /**
     * 获取用户路由
     */
    List<MenuTreeResponse> getUserRoutes();

    /**
     * 修改密码
     */
    void changePassword(ChangePasswordRequest request);
}