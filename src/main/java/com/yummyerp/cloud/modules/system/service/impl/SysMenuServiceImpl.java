package com.yummyerp.cloud.modules.system.service.impl;

import com.yummyerp.cloud.modules.system.dto.MenuTreeResponse;
import com.yummyerp.cloud.modules.system.entity.SysMenu;
import com.yummyerp.cloud.modules.system.entity.SysRoleMenu;
import com.yummyerp.cloud.modules.system.mapper.SysMenuMapper;
import com.yummyerp.cloud.modules.system.mapper.SysRoleMenuMapper;
import com.yummyerp.cloud.modules.system.mapper.SysUserMapper;
import com.yummyerp.cloud.modules.system.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<MenuTreeResponse> getUserRoutes(Long userId) {
        List<SysMenu> menus = getUserMenusByUserId(userId);
        return buildMenuTree(convertToMenuTreeResponse(menus));
    }

    @Override
    public List<SysMenu> getUserMenusByUserId(Long userId) {
        return sysUserMapper.getUserMenusByUserId(userId);
    }

    /**
     * 将SysMenu转换为MenuTreeResponse
     */
    private List<MenuTreeResponse> convertToMenuTreeResponse(List<SysMenu> menus) {
        return menus.stream().map(menu -> {
            MenuTreeResponse response = new MenuTreeResponse();
            response.setId(menu.getId().toString());
            response.setParentId(menu.getParentId() != null ? menu.getParentId().toString() : "0");
            response.setPath(menu.getPath());
            response.setComponent(menu.getComponent());
            response.setRedirect(menu.getRedirect());

            // 类型转换：Integer -> Integer 
            response.setType(menu.getType() != null ? menu.getType() : 1);

            response.setTitle(menu.getTitle());
            response.setSvgIcon(menu.getSvgIcon());
            response.setIcon(menu.getIcon());
            response.setKeepAlive(menu.getKeepAlive());
            response.setHidden(menu.getHidden());
            response.setSort(menu.getSort());
            response.setActiveMenu(menu.getActiveMenu());
            response.setBreadcrumb(menu.getBreadcrumb());

            // 状态转换：Integer -> Integer 
            response.setStatus(menu.getStatus() != null ? menu.getStatus() : 0);

            response.setPermission(menu.getPermission());
            response.setShowInTabs(menu.getShowInTabs());
            response.setAffix(menu.getAffix());
            response.setAlwaysShow(menu.getAlwaysShow());

            // 处理角色信息
            List<String> roles = new ArrayList<>();
            if (menu.getRoleCodes() != null && !menu.getRoleCodes().trim().isEmpty()) {
                roles = Arrays.asList(menu.getRoleCodes().split(","));
            }
            response.setRoles(roles);

            return response;
        }).collect(Collectors.toList());
    }

    /**
     * 构建菜单树形结构
     */
    private List<MenuTreeResponse> buildMenuTree(List<MenuTreeResponse> menus) {
        Map<String, MenuTreeResponse> menuMap = new HashMap<>();
        List<MenuTreeResponse> rootMenus = new ArrayList<>();

        // 先将所有菜单放入Map
        for (MenuTreeResponse menu : menus) {
            menuMap.put(menu.getId(), menu);
            menu.setChildren(new ArrayList<>());
        }

        // 构建树形结构
        for (MenuTreeResponse menu : menus) {
            String parentId = menu.getParentId();
            if ("0".equals(parentId) || parentId == null) {
                // 根节点
                rootMenus.add(menu);
            } else {
                // 子节点
                MenuTreeResponse parent = menuMap.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(menu);
                } else {
                    // 父菜单不存在（用户没有权限），将其作为根节点
                    rootMenus.add(menu);
                }
            }
        }

        // 按sort字段排序
        sortMenus(rootMenus);

        return rootMenus;
    }

    /**
     * 递归排序菜单
     */
    private void sortMenus(List<MenuTreeResponse> menus) {
        menus.sort(Comparator.comparing(menu -> menu.getSort() != null ? menu.getSort() : 0));
        for (MenuTreeResponse menu : menus) {
            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                sortMenus(menu.getChildren());
            }
        }
    }

    @Override
    public List<SysMenu> getMenuTreeList(String title, Integer status) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();

        // 查询条件
        if (title != null && !title.trim().isEmpty()) {
            queryWrapper.like("title", title);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("sort");

        List<SysMenu> allMenus = this.list(queryWrapper);

        // 构建树形结构
        return buildMenuTreeFromList(allMenus, 0L);
    }

    @Override
    public List<Map<String, Object>> getMenuOptions() {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", 0);
        queryWrapper.eq("status", 1);
        queryWrapper.orderByAsc("sort");

        List<SysMenu> allMenus = this.list(queryWrapper);

        // 转换为选项格式
        List<Map<String, Object>> options = convertToMenuOptions(allMenus);

        // 构建树形选项结构
        return buildMenuOptionsTree(options, 0L);
    }

    /**
     * 从菜单列表构建树形结构
     */
    private List<SysMenu> buildMenuTreeFromList(List<SysMenu> menus, Long parentId) {
        List<SysMenu> children = new ArrayList<>();

        for (SysMenu menu : menus) {
            Long menuParentId = menu.getParentId() != null ? menu.getParentId() : 0L;
            if (menuParentId.equals(parentId)) {
                // 递归查找子菜单
                List<SysMenu> subMenus = buildMenuTreeFromList(menus, menu.getId());
                // 将子菜单设置到当前菜单的children属性中（需要在SysMenu实体中添加children字段）
                menu.setChildren(subMenus);
                children.add(menu);
            }
        }

        // 按sort排序
        children.sort(Comparator.comparing(menu -> menu.getSort() != null ? menu.getSort() : 0));

        return children;
    }

    /**
     * 转换为菜单选项格式
     */
    private List<Map<String, Object>> convertToMenuOptions(List<SysMenu> menus) {
        return menus.stream().map(menu -> {
            Map<String, Object> option = new HashMap<>();
            option.put("id", menu.getId().toString());
            option.put("title", menu.getTitle());
            option.put("parentId", menu.getParentId() != null ? menu.getParentId().toString() : "0");
            return option;
        }).collect(Collectors.toList());
    }

    /**
     * 构建菜单选项树形结构
     */
    private List<Map<String, Object>> buildMenuOptionsTree(List<Map<String, Object>> options, Long parentId) {
        List<Map<String, Object>> children = new ArrayList<>();

        for (Map<String, Object> option : options) {
            String optionParentId = (String) option.get("parentId");
            Long menuParentId = "0".equals(optionParentId) ? 0L : Long.parseLong(optionParentId);

            if (menuParentId.equals(parentId)) {
                // 递归查找子选项
                String optionId = (String) option.get("id");
                List<Map<String, Object>> subOptions = buildMenuOptionsTree(options, Long.parseLong(optionId));
                if (!subOptions.isEmpty()) {
                    option.put("children", subOptions);
                }
                children.add(option);
            }
        }

        return children;
    }

    @Override
    public boolean deleteMenuWithRelations(List<Long> menuIds) {
        if (menuIds == null || menuIds.isEmpty()) {
            return false;
        }

        // 先删除角色菜单关联表中的数据
        QueryWrapper<SysRoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.in("menu_id", menuIds);
        sysRoleMenuMapper.delete(roleMenuQueryWrapper);

        // 再删除菜单本身
        return this.removeByIds(menuIds);
    }

    @Override
    public int cleanupInvalidRoleMenuRelations() {
        // 查询所有存在的菜单ID
        QueryWrapper<SysMenu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.select("id");
        List<SysMenu> existingMenus = this.list(menuQueryWrapper);
        List<Long> existingMenuIds = existingMenus.stream()
                .map(SysMenu::getId)
                .collect(Collectors.toList());

        if (existingMenuIds.isEmpty()) {
            // 如果没有任何菜单，删除所有角色菜单关联记录
            QueryWrapper<SysRoleMenu> deleteAllWrapper = new QueryWrapper<>();
            return sysRoleMenuMapper.delete(deleteAllWrapper);
        }

        // 查询sys_role_menu中不存在于菜单表的记录并删除
        QueryWrapper<SysRoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.notIn("menu_id", existingMenuIds);

        return sysRoleMenuMapper.delete(roleMenuQueryWrapper);
    }
}
