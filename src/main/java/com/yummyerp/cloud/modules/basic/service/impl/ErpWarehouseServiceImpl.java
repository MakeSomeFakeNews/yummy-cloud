package com.yummyerp.cloud.modules.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yummyerp.cloud.modules.basic.dto.ErpWarehouseQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpWarehouse;
import com.yummyerp.cloud.modules.basic.mapper.ErpWarehouseMapper;
import com.yummyerp.cloud.modules.basic.service.ErpWarehouseService;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 仓库信息表 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
public class ErpWarehouseServiceImpl extends ServiceImpl<ErpWarehouseMapper, ErpWarehouse> implements ErpWarehouseService {

    @Override
    public PageResult<ErpWarehouse> getWarehousePageList(PageRequest pageRequest, ErpWarehouseQuery query) {
        LambdaQueryWrapper<ErpWarehouse> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(query.getCode())) {
            wrapper.like(ErpWarehouse::getCode, query.getCode());
        }
        if (StringUtils.hasText(query.getName())) {
            wrapper.like(ErpWarehouse::getName, query.getName());
        }
        if (query.getType() != null) {
            wrapper.eq(ErpWarehouse::getType, query.getType());
        }
        if (StringUtils.hasText(query.getManager())) {
            wrapper.like(ErpWarehouse::getManager, query.getManager());
        }
        if (StringUtils.hasText(query.getPhone())) {
            wrapper.like(ErpWarehouse::getPhone, query.getPhone());
        }
        if (query.getStatus() != null) {
            wrapper.eq(ErpWarehouse::getStatus, query.getStatus());
        }

        // 动态排序处理
        if (StringUtils.hasText(pageRequest.getSortField())) {
            String sortField = pageRequest.getSortField();
            boolean isDesc = pageRequest.isDesc();
            
            switch (sortField) {
                case "code":
                    wrapper.orderBy(true, !isDesc, ErpWarehouse::getCode);
                    break;
                case "name":
                    wrapper.orderBy(true, !isDesc, ErpWarehouse::getName);
                    break;
                case "type":
                    wrapper.orderBy(true, !isDesc, ErpWarehouse::getType);
                    break;
                case "status":
                    wrapper.orderBy(true, !isDesc, ErpWarehouse::getStatus);
                    break;
                case "createTime":
                    wrapper.orderBy(true, !isDesc, ErpWarehouse::getCreateTime);
                    break;
                case "updateTime":
                    wrapper.orderBy(true, !isDesc, ErpWarehouse::getUpdateTime);
                    break;
                default:
                    wrapper.orderByDesc(ErpWarehouse::getCreateTime);
                    break;
            }
        } else {
            // 默认按创建时间倒序
            wrapper.orderByDesc(ErpWarehouse::getCreateTime);
        }

        Page<ErpWarehouse> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        Page<ErpWarehouse> result = this.page(page, wrapper);

        return PageResult.of(result);
    }

    @Override
    public List<Map<String, Object>> getWarehouseOptions(String keyword, Integer size) {
        LambdaQueryWrapper<ErpWarehouse> wrapper = new LambdaQueryWrapper<>();
        
        // 只查询启用状态的仓库
        wrapper.eq(ErpWarehouse::getStatus, 1);
        
        // 如果有关键词，按仓库编码或名称模糊搜索
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(ErpWarehouse::getCode, keyword)
                    .or()
                    .like(ErpWarehouse::getName, keyword));
        }
        
        // 按创建时间倒序，限制数量
        wrapper.orderByDesc(ErpWarehouse::getCreateTime);
        wrapper.last("LIMIT " + size);
        
        List<ErpWarehouse> warehouses = this.list(wrapper);
        
        return warehouses.stream()
                .map(warehouse -> {
                    Map<String, Object> option = new HashMap<>();
                    option.put("label", warehouse.getName());
                    option.put("value", warehouse.getId());
                    return option;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getWarehouseOptionById(Long id) {
        ErpWarehouse warehouse = this.getById(id);
        if (warehouse != null && warehouse.getStatus() == 1) {
            Map<String, Object> option = new HashMap<>();
            option.put("label", warehouse.getName());
            option.put("value", warehouse.getId());
            return option;
        }
        return null;
    }

}
