package com.yummyerp.cloud.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yummyerp.cloud.modules.system.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 获取用户分页列表
     */
    Map<String, Object> getUserPageList(Integer page, Integer size, String username, Integer status, Long deptId);

    /**
     * 根据ID获取用户详情（包含角色、部门信息）
     */
    SysUser getUserDetailById(Long userId);

    /**
     * 保存用户及角色关联
     */
    boolean saveUserWithRoles(SysUser sysUser);

    /**
     * 更新用户及角色关联
     */
    boolean updateUserWithRoles(SysUser sysUser);

    /**
     * 删除用户及相关关联数据
     */
    boolean deleteUsersWithRelations(List<Long> userIds);
    
    /**
     * 根据ID获取用户基本信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUser getUserById(Long userId);
}
