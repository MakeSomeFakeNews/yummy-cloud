package com.yummyerp.cloud.modules.system.service;

import com.yummyerp.cloud.modules.system.dto.MenuTreeResponse;
import com.yummyerp.cloud.modules.system.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据用户ID获取用户菜单路由
     */
    List<MenuTreeResponse> getUserRoutes(Long userId);

    /**
     * 根据用户ID获取用户菜单
     */
    List<SysMenu> getUserMenusByUserId(Long userId);

    /**
     * 获取菜单树形列表
     */
    List<SysMenu> getMenuTreeList(String title, Integer status);

    /**
     * 获取菜单选项（用于下拉选择）
     */
    List<Map<String, Object>> getMenuOptions();

    /**
     * 删除菜单及相关关联数据
     */
    boolean deleteMenuWithRelations(List<Long> menuIds);

    /**
     * 清理sys_role_menu表中不存在的菜单关联记录
     */
    int cleanupInvalidRoleMenuRelations();
}
