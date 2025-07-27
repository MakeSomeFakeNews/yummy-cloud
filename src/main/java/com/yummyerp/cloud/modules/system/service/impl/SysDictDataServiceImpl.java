package com.yummyerp.cloud.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yummyerp.cloud.modules.system.entity.SysDict;
import com.yummyerp.cloud.modules.system.entity.SysDictData;
import com.yummyerp.cloud.modules.system.mapper.SysDictDataMapper;
import com.yummyerp.cloud.modules.system.mapper.SysDictMapper;
import com.yummyerp.cloud.modules.system.service.SysDictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统字典数据表 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public boolean save(SysDictData entity) {
        // 如果没有dictCode但有dictId，则根据dictId查找对应的dictCode
        if ((entity.getDictCode() == null || entity.getDictCode().isEmpty()) && entity.getDictId() != null) {
            SysDict dict = sysDictMapper.selectById(entity.getDictId());
            if (dict != null) {
                entity.setDictCode(dict.getCode());
            }
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(SysDictData entity) {
        // 如果没有dictCode但有dictId，则根据dictId查找对应的dictCode
        if ((entity.getDictCode() == null || entity.getDictCode().isEmpty()) && entity.getDictId() != null) {
            SysDict dict = sysDictMapper.selectById(entity.getDictId());
            if (dict != null) {
                entity.setDictCode(dict.getCode());
            }
        }
        return super.updateById(entity);
    }
}
