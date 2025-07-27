package com.yummyerp.cloud.modules.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yummyerp.cloud.modules.system.dto.*;
import com.yummyerp.cloud.modules.system.entity.SysUser;
import com.yummyerp.cloud.modules.system.mapper.SysUserMapper;
import com.yummyerp.cloud.modules.system.service.CaptchaService;
import com.yummyerp.cloud.modules.system.service.SysMenuService;
import com.yummyerp.cloud.modules.system.service.SysRoleService;
import com.yummyerp.cloud.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public CaptchaResponse getCaptcha() {
        return captchaService.generateCaptcha();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // 验证验证码
        if (!captchaService.verifyCaptcha(request.getCaptchaId(), request.getCaptcha())) {
            throw new RuntimeException("验证码错误或已过期");
        }

        // 删除验证码
        captchaService.deleteCaptcha(request.getCaptchaId());

        // 查询用户
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, request.getUsername());
        SysUser user = sysUserMapper.selectOne(wrapper);

        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 检查用户状态
        if (user.getStatus() != 1) {
            throw new RuntimeException("用户已被禁用");
        }

        // 执行登录
        StpUtil.login(user.getId());

        return new LoginResponse(StpUtil.getTokenValue());
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public UserInfoResponse getUserInfo() {
        // 获取当前登录用户ID
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询用户信息
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        UserInfoResponse response = new UserInfoResponse();
        response.setId(user.getId().toString());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());

        // 获取用户角色和权限
        List<String> roles = sysRoleService.getUserRoles(userId);
        List<String> permissions = sysRoleService.getUserPermissions(userId);

        response.setRoles(roles);
        response.setPermissions(permissions);

        return response;
    }

    @Override
    public List<MenuTreeResponse> getUserRoutes() {
        // 获取当前登录用户ID
        Long userId = StpUtil.getLoginIdAsLong();

        // 获取用户菜单路由
        return sysMenuService.getUserRoutes(userId);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        // 获取当前登录用户ID
        Long userId = StpUtil.getLoginIdAsLong();

        // 查询用户信息
        SysUser user = sysUserMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证当前密码
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("当前密码错误");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        int result = sysUserMapper.updateById(user);
        
        if (result != 1) {
            throw new RuntimeException("密码修改失败");
        }
    }
}