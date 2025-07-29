package com.yummyerp.cloud.modules.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yummyerp.cloud.modules.basic.dto.ErpSupplierQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpSupplier;
import com.yummyerp.cloud.modules.basic.mapper.ErpSupplierMapper;
import com.yummyerp.cloud.modules.basic.service.ErpSupplierService;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 供应商信息表 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
public class ErpSupplierServiceImpl extends ServiceImpl<ErpSupplierMapper, ErpSupplier> implements ErpSupplierService {

    @Override
    public PageResult<ErpSupplier> getSupplierPageList(PageRequest pageRequest, ErpSupplierQuery query) {
        LambdaQueryWrapper<ErpSupplier> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(query.getCode())) {
            wrapper.like(ErpSupplier::getCode, query.getCode());
        }
        if (StringUtils.hasText(query.getName())) {
            wrapper.like(ErpSupplier::getName, query.getName());
        }
        if (query.getType() != null) {
            wrapper.eq(ErpSupplier::getType, query.getType());
        }
        if (query.getLevel() != null) {
            wrapper.eq(ErpSupplier::getLevel, query.getLevel());
        }
        if (StringUtils.hasText(query.getContactPerson())) {
            wrapper.like(ErpSupplier::getContactPerson, query.getContactPerson());
        }
        if (StringUtils.hasText(query.getContactPhone())) {
            wrapper.like(ErpSupplier::getContactPhone, query.getContactPhone());
        }
        if (query.getStatus() != null) {
            wrapper.eq(ErpSupplier::getStatus, query.getStatus());
        }
        
        wrapper.orderByDesc(ErpSupplier::getCreateTime);
        
        Page<ErpSupplier> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        Page<ErpSupplier> result = this.page(page, wrapper);

        return PageResult.of(result);
    }
}
