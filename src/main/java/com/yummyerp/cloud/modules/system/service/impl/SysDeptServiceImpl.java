package com.yummyerp.cloud.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yummyerp.cloud.modules.system.dto.SysDeptQuery;
import com.yummyerp.cloud.modules.system.entity.SysDept;
import com.yummyerp.cloud.modules.system.entity.SysRoleDept;
import com.yummyerp.cloud.modules.system.entity.SysUser;
import com.yummyerp.cloud.modules.system.mapper.SysDeptMapper;
import com.yummyerp.cloud.modules.system.mapper.SysRoleDeptMapper;
import com.yummyerp.cloud.modules.system.mapper.SysUserMapper;
import com.yummyerp.cloud.modules.system.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 系统部门表 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;

    @Override
    public List<SysDept> getDeptTreeList(SysDeptQuery query) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (query.getName() != null && !query.getName().trim().isEmpty()) {
            queryWrapper.like(SysDept::getName, query.getName());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(SysDept::getStatus, query.getStatus());
        }
        if (query.getParentId() != null) {
            queryWrapper.eq(SysDept::getParentId, query.getParentId());
        }
        queryWrapper.eq(SysDept::getDeleted, 0);
        queryWrapper.orderByAsc(SysDept::getSort);
        
        List<SysDept> allDepts = this.list(queryWrapper);
        
        // 构建树形结构
        return buildDeptTreeFromList(allDepts, 0L);
    }

    @Override
    @Deprecated
    public List<SysDept> getDeptTreeList(String name, Integer status) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        if (name != null && !name.trim().isEmpty()) {
            queryWrapper.like(SysDept::getName, name);
        }
        if (status != null) {
            queryWrapper.eq(SysDept::getStatus, status);
        }
        queryWrapper.eq(SysDept::getDeleted, 0);
        queryWrapper.orderByAsc(SysDept::getSort);
        
        List<SysDept> allDepts = this.list(queryWrapper);
        
        // 构建树形结构
        return buildDeptTreeFromList(allDepts, 0L);
    }

    @Override
    @Transactional
    public void saveDeptWithAncestors(SysDept sysDept) {
        // 设置祖级信息
        setAncestors(sysDept);
        
        // 保存部门
        this.save(sysDept);
    }

    @Override
    @Transactional
    public void updateDeptWithAncestors(SysDept sysDept) {
        // 设置祖级信息
        setAncestors(sysDept);
        
        // 更新部门
        boolean result = this.updateById(sysDept);
        
        if (result) {
            // 更新子部门的祖级信息
            updateChildrenAncestors(sysDept);
        }

    }

    @Override
    @Transactional
    public boolean deleteDeptWithRelations(List<Long> deptIds) {
        if (deptIds == null || deptIds.isEmpty()) {
            return false;
        }
        
        // 检查是否有子部门
        LambdaQueryWrapper<SysDept> childWrapper = new LambdaQueryWrapper<>();
        childWrapper.in(SysDept::getParentId, deptIds);
        childWrapper.eq(SysDept::getDeleted, 0);
        long childCount = this.count(childWrapper);
        
        if (childCount > 0) {
            throw new RuntimeException("存在子部门，无法删除");
        }
        
        // 检查是否有用户
        LambdaQueryWrapper<SysUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.in(SysUser::getDeptId, deptIds);
        userWrapper.eq(SysUser::getDeleted, 0);
        long userCount = sysUserMapper.selectCount(userWrapper);
        
        if (userCount > 0) {
            throw new RuntimeException("部门下存在用户，无法删除");
        }
        
        // 删除角色部门关联
        LambdaQueryWrapper<SysRoleDept> roleDeptWrapper = new LambdaQueryWrapper<>();
        roleDeptWrapper.in(SysRoleDept::getDeptId, deptIds);
        sysRoleDeptMapper.delete(roleDeptWrapper);
        
        // 删除部门
        return this.removeByIds(deptIds);
    }

    /**
     * 从部门列表构建树形结构
     */
    private List<SysDept> buildDeptTreeFromList(List<SysDept> depts, Long parentId) {
        List<SysDept> children = new ArrayList<>();
        
        for (SysDept dept : depts) {
            Long deptParentId = dept.getParentId() != null ? dept.getParentId() : 0L;
            if (deptParentId.equals(parentId)) {
                // 递归查找子部门
                List<SysDept> subDepts = buildDeptTreeFromList(depts, dept.getId());
                dept.setChildren(subDepts);
                children.add(dept);
            }
        }
        
        // 按sort排序
        children.sort(Comparator.comparing(dept -> dept.getSort() != null ? dept.getSort() : 0));
        
        return children;
    }

    /**
     * 设置祖级信息
     */
    private void setAncestors(SysDept sysDept) {
        if (sysDept.getParentId() == null || sysDept.getParentId() == 0) {
            sysDept.setAncestors("0");
        } else {
            SysDept parentDept = this.getById(sysDept.getParentId());
            if (parentDept != null) {
                sysDept.setAncestors(parentDept.getAncestors() + "," + sysDept.getParentId());
            } else {
                sysDept.setAncestors("0");
            }
        }
    }

    /**
     * 更新子部门的祖级信息
     */
    private void updateChildrenAncestors(SysDept sysDept) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDept::getParentId, sysDept.getId());
        wrapper.eq(SysDept::getDeleted, 0);
        List<SysDept> children = this.list(wrapper);
        
        for (SysDept child : children) {
            child.setAncestors(sysDept.getAncestors() + "," + sysDept.getId());
            this.updateById(child);
            // 递归更新子部门的子部门
            updateChildrenAncestors(child);
        }
    }
}
