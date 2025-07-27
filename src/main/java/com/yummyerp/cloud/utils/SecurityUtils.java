package com.yummyerp.cloud.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.yummyerp.cloud.modules.system.entity.SysUser;
import com.yummyerp.cloud.modules.system.service.SysUserService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 安全工具类
 */
@Component
public class SecurityUtils implements ApplicationContextAware {

    private static SysUserService userService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        try {
            userService = applicationContext.getBean(SysUserService.class);
        } catch (NoSuchBeanDefinitionException e) {
            // 忽略异常，可能在某些场景下不需要使用用户服务
        }
    }

    /**
     * 获取当前用户ID
     *
     * @return 用户ID，如果未登录则返回null
     */
    public static Long getUserId() {
        try {
            return StpUtil.isLogin() ? StpUtil.getLoginIdAsLong() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前用户名
     *
     * @return 用户名，如果未找到则返回null
     */
    public static String getUsername() {
        try {
            if (StpUtil.isLogin() && userService != null) {
                SysUser user = userService.getUserById(StpUtil.getLoginIdAsLong());
                return user != null ? user.getUsername() : null;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前用户部门名称
     *
     * @return 部门名称，如果未找到则返回null
     */
    public static String getDeptName() {
        try {
            if (StpUtil.isLogin() && userService != null) {
                SysUser user = userService.getUserById(StpUtil.getLoginIdAsLong());
                return user != null ? user.getDeptName() : null;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断当前用户是否为管理员
     *
     * @return 是否为管理员
     */
    public static boolean isAdmin() {
        return isAdmin(getUserId());
    }

    /**
     * 判断指定用户是否为管理员
     *
     * @param userId 用户ID
     * @return 是否为管理员
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
} 