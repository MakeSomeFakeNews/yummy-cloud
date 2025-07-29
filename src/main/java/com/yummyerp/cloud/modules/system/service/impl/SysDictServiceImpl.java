package com.yummyerp.cloud.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();

        if (query.getName() != null && !query.getName().trim().isEmpty()) {
            queryWrapper.like(SysDict::getName, query.getName());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(SysDict::getStatus, query.getStatus());
        }
        if (query.getCode() != null && !query.getCode().trim().isEmpty()) {
            queryWrapper.like(SysDict::getCode, query.getCode());
        }
        Page<SysDict> pageResult = this.page(pageObj, queryWrapper);

        return PageResult.of(pageResult);
    }

    @Override
    @Deprecated
    public PageResult<SysDict> getDictPageList(Integer page, Integer size, String name, Integer status) {
        Page<SysDict> pageObj = new Page<>(page, size);
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();

        if (name != null && !name.trim().isEmpty()) {
            queryWrapper.like(SysDict::getName, name).or().like(SysDict::getCode, name);
        }
        if (status != null) {
            queryWrapper.eq(SysDict::getStatus, status);
        }
        queryWrapper.eq(SysDict::getDeleted, 0);
        queryWrapper.orderByAsc(SysDict::getSort);

        IPage<SysDict> pageResult = this.page(pageObj, queryWrapper);

        return PageResult.of((Page<SysDict>) pageResult);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDictsWithData(List<Long> dictIds) {
        if (dictIds == null || dictIds.isEmpty()) {
            return false;
        }

        // 删除字典数据（使用dict_id）
        LambdaQueryWrapper<SysDictData> dataWrapper = new LambdaQueryWrapper<>();
        dataWrapper.in(SysDictData::getDictId, dictIds);
        sysDictDataMapper.delete(dataWrapper);

        // 删除字典（逻辑删除）
        return this.removeByIds(dictIds);
    }

    @Override
    public PageResult<SysDictData> getDictDataPageList(String dictCode, Integer page, Integer size) {
        // 先根据字典编码获取字典ID
        LambdaQueryWrapper<SysDict> dictWrapper = new LambdaQueryWrapper<>();
        dictWrapper.eq(SysDict::getCode, dictCode);
        dictWrapper.eq(SysDict::getDeleted, 0);
        SysDict dict = this.getOne(dictWrapper);

        if (dict == null) {
            // 如果字典不存在，返回空结果
            return new PageResult<>((long) page, (long) size, 0L, new ArrayList<>());
        }

        Page<SysDictData> pageObj = new Page<>(page, size);
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(SysDictData::getDictId, dict.getId());
        queryWrapper.eq(SysDictData::getDeleted, 0);
        queryWrapper.orderByAsc(SysDictData::getSort);

        IPage<SysDictData> pageResult = sysDictDataMapper.selectPage(pageObj, queryWrapper);

        return PageResult.of((Page<SysDictData>) pageResult);
    }

    @Override
    public SysDictData getDictDataDetail(Long id) {
        return sysDictDataMapper.selectById(id);
    }

    @Override
    public Map<String, Object> getAllDictDataMap() {
        // 获取所有有效的字典
        LambdaQueryWrapper<SysDict> dictWrapper = new LambdaQueryWrapper<>();
        dictWrapper.eq(SysDict::getStatus, 1);
        dictWrapper.eq(SysDict::getDeleted, 0);
        List<SysDict> dicts = this.list(dictWrapper);

        Map<String, Object> result = new HashMap<>();

        for (SysDict dict : dicts) {
            // 获取每个字典的数据
            LambdaQueryWrapper<SysDictData> dataWrapper = new LambdaQueryWrapper<>();
            dataWrapper.eq(SysDictData::getDictId, dict.getId());
            dataWrapper.eq(SysDictData::getStatus, 1);
            dataWrapper.eq(SysDictData::getDeleted, 0);
            dataWrapper.orderByAsc(SysDictData::getSort);

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
