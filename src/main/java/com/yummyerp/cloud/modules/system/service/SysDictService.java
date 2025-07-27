package com.yummyerp.cloud.modules.system.service;

import com.yummyerp.cloud.modules.system.entity.SysDict;
import com.yummyerp.cloud.modules.system.entity.SysDictData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统字典表 服务类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 获取字典分页列表
     */
    Map<String, Object> getDictPageList(Integer page, Integer size, String name, Integer status);

    /**
     * 删除字典及相关数据
     */
    boolean deleteDictsWithData(List<Long> dictIds);

    /**
     * 获取字典数据分页列表
     */
    Map<String, Object> getDictDataPageList(String dictCode, Integer page, Integer size);

    /**
     * 获取字典数据详情
     */
    SysDictData getDictDataDetail(Long id);

    /**
     * 获取所有字典数据映射
     */
    Map<String, Object> getAllDictDataMap();
}
