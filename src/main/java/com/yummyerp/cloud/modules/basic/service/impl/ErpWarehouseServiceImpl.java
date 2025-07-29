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

        wrapper.orderByDesc(ErpWarehouse::getCreateTime);

        Page<ErpWarehouse> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        Page<ErpWarehouse> result = this.page(page, wrapper);

        return PageResult.of(result);
    }

}
