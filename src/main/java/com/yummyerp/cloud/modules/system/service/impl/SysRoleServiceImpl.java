package com.yummyerp.cloud.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yummyerp.cloud.modules.system.dto.UserRolePermissionDto;
import com.yummyerp.cloud.modules.system.entity.SysRole;
import com.yummyerp.cloud.modules.system.entity.SysRoleMenu;
import com.yummyerp.cloud.modules.system.entity.SysUserRole;
import com.yummyerp.cloud.modules.system.mapper.SysRoleMapper;
import com.yummyerp.cloud.modules.system.mapper.SysRoleMenuMapper;
import com.yummyerp.cloud.modules.system.mapper.SysUserMapper;
import com.yummyerp.cloud.modules.system.mapper.SysUserRoleMapper;
import com.yummyerp.cloud.modules.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public UserRolePermissionDto getUserRolePermissionByUserId(Long userId) {
        return sysUserMapper.getUserRolePermissionByUserId(userId);
    }

    @Override
    public List<String> getUserRoles(Long userId) {
        UserRolePermissionDto dto = getUserRolePermissionByUserId(userId);
        if (dto != null && dto.getRoleCodes() != null && !dto.getRoleCodes().trim().isEmpty()) {
            return Arrays.asList(dto.getRoleCodes().split(","));
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getUserPermissions(Long userId) {
        UserRolePermissionDto dto = getUserRolePermissionByUserId(userId);
        if (dto != null && dto.getPermissionCodes() != null && !dto.getPermissionCodes().trim().isEmpty()) {
            return Arrays.asList(dto.getPermissionCodes().split(","));
        }
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> getRolePageList(Integer page, Integer size, String name, Integer status) {
        Page<SysRole> pageObj = new Page<>(page, size);
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        
        if (name != null && !name.trim().isEmpty()) {
            queryWrapper.like("name", name);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("sort");
        
        IPage<SysRole> pageResult = this.page(pageObj, queryWrapper);
        
        Map<String, Object> result = new HashMap<>();
        result.put("records", pageResult.getRecords());
        result.put("total", pageResult.getTotal());
        result.put("current", pageResult.getCurrent());
        result.put("size", pageResult.getSize());
        result.put("pages", pageResult.getPages());
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRolesWithRelations(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return false;
        }
        
        // Delete role-menu relations
        QueryWrapper<SysRoleMenu> roleMenuWrapper = new QueryWrapper<>();
        roleMenuWrapper.in("role_id", roleIds);
        sysRoleMenuMapper.delete(roleMenuWrapper);
        
        // Delete user-role relations
        QueryWrapper<SysUserRole> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.in("role_id", roleIds);
        sysUserRoleMapper.delete(userRoleWrapper);
        
        // Delete roles (logical deletion)
        return this.removeByIds(roleIds);
    }

    @Override
    public List<String> getRoleMenuIds(String roleCode) {
        QueryWrapper<SysRole> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("code", roleCode);
        roleWrapper.eq("deleted", 0);
        SysRole role = this.getOne(roleWrapper);
        
        if (role == null) {
            return new ArrayList<>();
        }
        
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", role.getId());
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(wrapper);
        
        return roleMenus.stream()
                .map(rm -> rm.getMenuId().toString())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setRoleMenus(String roleCode, List<String> menuIds) {
        QueryWrapper<SysRole> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("code", roleCode);
        roleWrapper.eq("deleted", 0);
        SysRole role = this.getOne(roleWrapper);
        
        if (role == null) {
            return false;
        }
        
        // Delete existing role-menu relations
        QueryWrapper<SysRoleMenu> deleteWrapper = new QueryWrapper<>();
        deleteWrapper.eq("role_id", role.getId());
        sysRoleMenuMapper.delete(deleteWrapper);
        
        // Insert new role-menu relations
        if (menuIds != null && !menuIds.isEmpty()) {
            List<SysRoleMenu> roleMenus = menuIds.stream()
                    .map(menuId -> {
                        SysRoleMenu roleMenu = new SysRoleMenu();
                        roleMenu.setRoleId(role.getId());
                        roleMenu.setMenuId(Long.parseLong(menuId));
                        return roleMenu;
                    })
                    .collect(Collectors.toList());
            
            for (SysRoleMenu roleMenu : roleMenus) {
                sysRoleMenuMapper.insert(roleMenu);
            }
        }
        
        return true;
    }
}
