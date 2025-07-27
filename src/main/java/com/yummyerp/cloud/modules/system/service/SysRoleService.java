package com.yummyerp.cloud.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yummyerp.cloud.modules.system.dto.UserRolePermissionDto;
import com.yummyerp.cloud.modules.system.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据用户ID获取角色权限信息
     */
    UserRolePermissionDto getUserRolePermissionByUserId(Long userId);

    /**
     * 根据用户ID获取角色列表
     */
    List<String> getUserRoles(Long userId);

    /**
     * 根据用户ID获取权限列表
     */
    List<String> getUserPermissions(Long userId);

    /**
     * 获取角色分页列表
     */
    Map<String, Object> getRolePageList(Integer page, Integer size, String name, Integer status);

    /**
     * 删除角色及相关关联数据
     */
    boolean deleteRolesWithRelations(List<Long> roleIds);

    /**
     * 获取角色菜单权限ID列表
     */
    List<String> getRoleMenuIds(String roleCode);

    /**
     * 设置角色菜单权限
     */
    boolean setRoleMenus(String roleCode, List<String> menuIds);
}
