package com.yummyerp.cloud.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import com.yummyerp.cloud.modules.system.dto.SysDictQuery;
import com.yummyerp.cloud.modules.system.entity.SysDict;
import com.yummyerp.cloud.modules.system.entity.SysDictData;
import com.yummyerp.cloud.modules.system.mapper.SysDictDataMapper;
import com.yummyerp.cloud.modules.system.mapper.SysDictMapper;
import com.yummyerp.cloud.modules.system.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统字典表 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public PageResult<SysDict> getDictPageList(PageRequest pageRequest, SysDictQuery query) {
        Page<SysDict> pageObj = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();

        if (query.getName() != null && !query.getName().trim().isEmpty()) {
            queryWrapper.like("name", query.getName());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq("status", query.getStatus());
        }
        if (query.getType() != null && !query.getType().trim().isEmpty()) {
            queryWrapper.like("type", query.getType());
        }
        if (query.getCode() != null && !query.getCode().trim().isEmpty()) {
            queryWrapper.like("code", query.getCode());
        }
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("sort");

        IPage<SysDict> pageResult = this.page(pageObj, queryWrapper);

        return PageResult.of((Page<SysDict>) pageResult);
    }

    @Override
    @Deprecated
    public Map<String, Object> getDictPageList(Integer page, Integer size, String name, Integer status) {
        Page<SysDict> pageObj = new Page<>(page, size);
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();

        if (name != null && !name.trim().isEmpty()) {
            queryWrapper.like("name", name).or().like("code", name);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("sort");

        IPage<SysDict> pageResult = this.page(pageObj, queryWrapper);

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
    public boolean deleteDictsWithData(List<Long> dictIds) {
        if (dictIds == null || dictIds.isEmpty()) {
            return false;
        }

        // 删除字典数据（使用dict_id）
        if (!dictIds.isEmpty()) {
            QueryWrapper<SysDictData> dataWrapper = new QueryWrapper<>();
            dataWrapper.in("dict_id", dictIds);
            sysDictDataMapper.delete(dataWrapper);
        }

        // 删除字典（逻辑删除）
        return this.removeByIds(dictIds);
    }

    @Override
    public Map<String, Object> getDictDataPageList(String dictCode, Integer page, Integer size) {
        // 先根据字典编码获取字典ID
        QueryWrapper<SysDict> dictWrapper = new QueryWrapper<>();
        dictWrapper.eq("code", dictCode);
        dictWrapper.eq("deleted", 0);
        SysDict dict = this.getOne(dictWrapper);

        if (dict == null) {
            // 如果字典不存在，返回空结果
            Map<String, Object> result = new HashMap<>();
            result.put("records", new ArrayList<>());
            result.put("total", 0);
            result.put("current", page);
            result.put("size", size);
            result.put("pages", 0);
            return result;
        }

        Page<SysDictData> pageObj = new Page<>(page, size);
        QueryWrapper<SysDictData> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("dict_id", dict.getId());
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByAsc("sort");

        IPage<SysDictData> pageResult = sysDictDataMapper.selectPage(pageObj, queryWrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("records", pageResult.getRecords());
        result.put("total", pageResult.getTotal());
        result.put("current", pageResult.getCurrent());
        result.put("size", pageResult.getSize());
        result.put("pages", pageResult.getPages());

        return result;
    }

    @Override
    public SysDictData getDictDataDetail(Long id) {
        return sysDictDataMapper.selectById(id);
    }

    @Override
    public Map<String, Object> getAllDictDataMap() {
        // 获取所有有效的字典
        QueryWrapper<SysDict> dictWrapper = new QueryWrapper<>();
        dictWrapper.eq("status", 1);
        dictWrapper.eq("deleted", 0);
        List<SysDict> dicts = this.list(dictWrapper);

        Map<String, Object> result = new HashMap<>();

        for (SysDict dict : dicts) {
            // 获取每个字典的数据
            QueryWrapper<SysDictData> dataWrapper = new QueryWrapper<>();
            dataWrapper.eq("dict_id", dict.getId());
            dataWrapper.eq("status", 1);
            dataWrapper.eq("deleted", 0);
            dataWrapper.orderByAsc("sort");

            List<SysDictData> dataList = sysDictDataMapper.selectList(dataWrapper);

            List<Map<String, Object>> options = dataList.stream()
                    .map(data -> {
                        Map<String, Object> option = new HashMap<>();
                        option.put("label", data.getName());
                        option.put("value", data.getValue());
                        return option;
                    })
                    .collect(Collectors.toList());

            result.put(dict.getCode(), options);
        }

        return result;
    }

    @Override
    public SysDict getByDictCode(String dictCode) {
        LambdaQueryWrapper<SysDict> sysDictLambdaQueryWrapper = new LambdaQueryWrapper<>();
        return baseMapper.selectOne(sysDictLambdaQueryWrapper.eq(SysDict::getCode, dictCode));
    }
}
