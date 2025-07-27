package com.yummyerp.cloud.modules.system.service;

import com.yummyerp.cloud.modules.system.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统部门表 服务类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 获取部门树形列表
     */
    List<SysDept> getDeptTreeList(String name, Integer status);

    /**
     * 保存部门及祖级信息
     */
    void saveDeptWithAncestors(SysDept sysDept);

    /**
     * 更新部门及祖级信息
     */
    void updateDeptWithAncestors(SysDept sysDept);

    /**
     * 删除部门及相关关联数据
     */
    boolean deleteDeptWithRelations(List<Long> deptIds);
}
