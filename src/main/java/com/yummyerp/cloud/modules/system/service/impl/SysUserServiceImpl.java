package com.yummyerp.cloud.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import com.yummyerp.cloud.modules.system.dto.SysUserQuery;
import com.yummyerp.cloud.modules.system.entity.SysDept;
import com.yummyerp.cloud.modules.system.entity.SysUser;
import com.yummyerp.cloud.modules.system.entity.SysUserRole;
import com.yummyerp.cloud.modules.system.mapper.SysDeptMapper;
import com.yummyerp.cloud.modules.system.mapper.SysUserMapper;
import com.yummyerp.cloud.modules.system.mapper.SysUserRoleMapper;
import com.yummyerp.cloud.modules.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    
    @Autowired
    private SysDeptMapper sysDeptMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public PageResult<SysUser> getUserPageList(PageRequest pageRequest, SysUserQuery query) {
        Page<SysUser> pageObj = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (query.getUsername() != null && !query.getUsername().trim().isEmpty()) {
            queryWrapper.like(SysUser::getUsername, query.getUsername());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(SysUser::getStatus, query.getStatus());
        }
        if (query.getDeptId() != null) {
            // 获取该部门及其所有子部门的ID列表
            List<Long> deptIds = getDeptAndChildrenIds(query.getDeptId());
            if (!deptIds.isEmpty()) {
                queryWrapper.in(SysUser::getDeptId, deptIds);
            }
        }
        if (query.getPhonenumber() != null && !query.getPhonenumber().trim().isEmpty()) {
            queryWrapper.like(SysUser::getPhone, query.getPhonenumber());
        }
        if (query.getEmail() != null && !query.getEmail().trim().isEmpty()) {
            queryWrapper.like(SysUser::getEmail, query.getEmail());
        }
        if (query.getRealName() != null && !query.getRealName().trim().isEmpty()) {
            queryWrapper.like(SysUser::getRealName, query.getRealName());
        }
        queryWrapper.eq(SysUser::getDeleted, 0);
        queryWrapper.orderByDesc(SysUser::getCreateTime);
        
        Page<SysUser> pageResult = this.page(pageObj, queryWrapper);
        
        // 填充用户的角色和部门信息
        List<SysUser> users = pageResult.getRecords();
        for (SysUser user : users) {
            fillUserExtraInfo(user);
        }
        
        return PageResult.of(pageResult);
    }

    @Override
    @Deprecated
    public PageResult<SysUser> getUserPageList(PageRequest pageRequest, String username, Integer status, Long deptId) {
        Page<SysUser> pageObj = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (username != null && !username.trim().isEmpty()) {
            queryWrapper.like(SysUser::getUsername, username);
        }
        if (status != null) {
            queryWrapper.eq(SysUser::getStatus, status);
        }
        if (deptId != null) {
            // 获取该部门及其所有子部门的ID列表
            List<Long> deptIds = getDeptAndChildrenIds(deptId);
            if (!deptIds.isEmpty()) {
                queryWrapper.in(SysUser::getDeptId, deptIds);
            }
        }
        queryWrapper.eq(SysUser::getDeleted, 0);
        queryWrapper.orderByDesc(SysUser::getCreateTime);
        
        Page<SysUser> pageResult = this.page(pageObj, queryWrapper);
        
        // 填充用户的角色和部门信息
        List<SysUser> users = pageResult.getRecords();
        for (SysUser user : users) {
            fillUserExtraInfo(user);
        }
        
        return PageResult.of(pageResult);
    }

    @Override
    @Deprecated
    public PageResult<SysUser> getUserPageList(Integer page, Integer size, String username, Integer status, Long deptId) {
        Page<SysUser> pageObj = new Page<>(page, size);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (username != null && !username.trim().isEmpty()) {
            queryWrapper.like(SysUser::getUsername, username);
        }
        if (status != null) {
            queryWrapper.eq(SysUser::getStatus, status);
        }
        if (deptId != null) {
            // 获取该部门及其所有子部门的ID列表
            List<Long> deptIds = getDeptAndChildrenIds(deptId);
            if (!deptIds.isEmpty()) {
                queryWrapper.in(SysUser::getDeptId, deptIds);
            }
        }
        queryWrapper.eq(SysUser::getDeleted, 0);
        queryWrapper.orderByDesc(SysUser::getCreateTime);
        
        Page<SysUser> pageResult = this.page(pageObj, queryWrapper);
        
        // 填充用户的角色和部门信息
        List<SysUser> users = pageResult.getRecords();
        for (SysUser user : users) {
            fillUserExtraInfo(user);
        }
        
        return PageResult.of(pageResult);
    }

    @Override
    public SysUser getUserDetailById(Long userId) {
        SysUser user = this.getById(userId);
        if (user != null) {
            fillUserExtraInfo(user);
        }
        return user;
    }

    @Override
    @Transactional
    public boolean saveUserWithRoles(SysUser sysUser) {
        // 加密密码
        if (sysUser.getPassword() != null && !sysUser.getPassword().isEmpty()) {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        } else {
            // 默认密码
            sysUser.setPassword(passwordEncoder.encode("123456"));
        }
        
        // 保存用户
        boolean result = this.save(sysUser);
        
        if (result && sysUser.getRoleIds() != null && !sysUser.getRoleIds().isEmpty()) {
            // 保存用户角色关联
            saveUserRoles(sysUser.getId(), sysUser.getRoleIds());
        }
        
        return result;
    }

    @Override
    @Transactional
    public boolean updateUserWithRoles(SysUser sysUser) {
        // 更新用户信息
        boolean result = this.updateById(sysUser);
        
        if (result) {
            // 删除原有的用户角色关联
            LambdaQueryWrapper<SysUserRole> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.eq(SysUserRole::getUserId, sysUser.getId());
            sysUserRoleMapper.delete(deleteWrapper);
            
            // 保存新的用户角色关联
            if (sysUser.getRoleIds() != null && !sysUser.getRoleIds().isEmpty()) {
                saveUserRoles(sysUser.getId(), sysUser.getRoleIds());
            }
        }
        
        return result;
    }

    @Override
    @Transactional
    public boolean deleteUsersWithRelations(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return false;
        }
        
        // 删除用户角色关联
        LambdaQueryWrapper<SysUserRole> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.in(SysUserRole::getUserId, userIds);
        sysUserRoleMapper.delete(roleWrapper);
        
        // 删除用户
        return this.removeByIds(userIds);
    }
    
    @Override
    public SysUser getUserById(Long userId) {
        if (userId == null) {
            return null;
        }
        
        SysUser user = this.getById(userId);
        
        // 如果找到用户，填充部门信息
        if (user != null && user.getDeptId() != null) {
            SysDept dept = sysDeptMapper.selectById(user.getDeptId());
            if (dept != null) {
                user.setDeptName(dept.getName());
            }
        }
        
        return user;
    }
    
    /**
     * 填充用户额外信息（部门名称、角色信息等）
     */
    private void fillUserExtraInfo(SysUser user) {
        // 设置禁用状态（系统内置用户不能删除和编辑）
        user.setDisabled(user.getType() != null && user.getType() == 1);
        
        // 填充部门名称
        if (user.getDeptId() != null) {
            SysDept dept = sysDeptMapper.selectById(user.getDeptId());
            if (dept != null) {
                user.setDeptName(dept.getName());
            }
        }
        
        // TODO: 填充创建用户名称
        // user.setCreateUserString(getUsernameById(user.getCreateUserId()));
        
        // 填充用户角色信息
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, user.getId());
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(wrapper);
        
        if (!userRoles.isEmpty()) {
            List<String> roleIds = userRoles.stream()
                    .map(ur -> ur.getRoleId().toString())
                    .collect(Collectors.toList());
            user.setRoleIds(roleIds);
            
            // TODO: 获取角色名称
            // user.setRoleNames(getRoleNamesByIds(roleIds));
        } else {
            user.setRoleIds(new ArrayList<>());
            user.setRoleNames(new ArrayList<>());
        }
    }

    /**
     * 保存用户角色关联
     */
    private void saveUserRoles(Long userId, List<String> roleIds) {
        List<SysUserRole> userRoles = roleIds.stream()
                .map(roleId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(Long.parseLong(roleId));
                    return userRole;
                })
                .collect(Collectors.toList());
        
        for (SysUserRole userRole : userRoles) {
            sysUserRoleMapper.insert(userRole);
        }
    }

    /**
     * 获取部门及其所有子部门的ID列表
     */
    private List<Long> getDeptAndChildrenIds(Long deptId) {
        List<Long> deptIds = new ArrayList<>();
        
        // 先获取当前部门信息
        SysDept currentDept = sysDeptMapper.selectById(deptId);
        if (currentDept == null) {
            return deptIds;
        }
        
        // 添加当前部门ID
        deptIds.add(deptId);
        
        // 查询所有部门，筛选出子部门
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDept::getDeleted, 0);
        List<SysDept> allDepts = sysDeptMapper.selectList(wrapper);
        
        // 使用ancestors字段找出所有子部门
        String currentAncestors = currentDept.getAncestors() + "," + deptId;
        
        for (SysDept dept : allDepts) {
            if (dept.getAncestors() != null && dept.getAncestors().startsWith(currentAncestors)) {
                deptIds.add(dept.getId());
            }
        }
        
        return deptIds;
    }
}
