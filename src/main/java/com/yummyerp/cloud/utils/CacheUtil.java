package com.yummyerp.cloud.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis缓存工具类
 */
@Component
public class CacheUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String USER_ROLES_PREFIX = "user:roles:";
    private static final String USER_MENUS_PREFIX = "user:menus:";
    private static final int USER_CACHE_EXPIRE_MINUTES = 30;

    /**
     * 获取用户角色权限缓存
     */
    public String getUserRolePermissionCache(Long userId) {
        return redisTemplate.opsForValue().get(USER_ROLES_PREFIX + userId);
    }

    /**
     * 设置用户角色权限缓存
     */
    public void setUserRolePermissionCache(Long userId, String roles, String permissions) {
        String cacheValue = roles + "|" + permissions;
        redisTemplate.opsForValue().set(
            USER_ROLES_PREFIX + userId, 
            cacheValue, 
            USER_CACHE_EXPIRE_MINUTES, 
            TimeUnit.MINUTES
        );
    }

    /**
     * 设置空值缓存（防止缓存穿透）
     */
    public void setNullCache(String key, int expireMinutes) {
        redisTemplate.opsForValue().set(key, "null", expireMinutes, TimeUnit.MINUTES);
    }

    /**
     * 获取用户菜单缓存标记
     */
    public String getUserMenuCache(Long userId) {
        return redisTemplate.opsForValue().get(USER_MENUS_PREFIX + userId);
    }

    /**
     * 设置用户菜单缓存标记
     */
    public void setUserMenuCache(Long userId, boolean hasData) {
        String cacheValue = hasData ? "cached" : "null";
        int expireTime = hasData ? USER_CACHE_EXPIRE_MINUTES : 5;
        redisTemplate.opsForValue().set(
            USER_MENUS_PREFIX + userId, 
            cacheValue, 
            expireTime, 
            TimeUnit.MINUTES
        );
    }

    /**
     * 删除用户相关缓存
     */
    public void deleteUserCache(Long userId) {
        redisTemplate.delete(USER_ROLES_PREFIX + userId);
        redisTemplate.delete(USER_MENUS_PREFIX + userId);
    }
}