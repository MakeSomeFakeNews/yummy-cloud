package com.yummyerp.cloud.modules.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yummyerp.cloud.modules.basic.dto.ErpCustomerQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpCustomer;
import com.yummyerp.cloud.modules.basic.mapper.ErpCustomerMapper;
import com.yummyerp.cloud.modules.basic.service.ErpCustomerService;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 客户信息表 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
public class ErpCustomerServiceImpl extends ServiceImpl<ErpCustomerMapper, ErpCustomer> implements ErpCustomerService {

    @Override
    public PageResult<ErpCustomer> getCustomerPageList(PageRequest pageRequest, ErpCustomerQuery query) {
        LambdaQueryWrapper<ErpCustomer> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(query.getCode())) {
            wrapper.like(ErpCustomer::getCode, query.getCode());
        }
        if (StringUtils.hasText(query.getName())) {
            wrapper.like(ErpCustomer::getName, query.getName());
        }
        if (query.getType() != null) {
            wrapper.eq(ErpCustomer::getType, query.getType());
        }
        if (query.getLevel() != null) {
            wrapper.eq(ErpCustomer::getLevel, query.getLevel());
        }
        if (StringUtils.hasText(query.getContactPerson())) {
            wrapper.like(ErpCustomer::getContactPerson, query.getContactPerson());
        }
        if (StringUtils.hasText(query.getContactPhone())) {
            wrapper.like(ErpCustomer::getContactPhone, query.getContactPhone());
        }
        if (query.getStatus() != null) {
            wrapper.eq(ErpCustomer::getStatus, query.getStatus());
        }
        if (StringUtils.hasText(query.getIndustry())) {
            wrapper.like(ErpCustomer::getIndustry, query.getIndustry());
        }

        // 动态排序处理
        if (StringUtils.hasText(pageRequest.getSortField())) {
            String sortField = pageRequest.getSortField();
            boolean isDesc = pageRequest.isDesc();
            
            switch (sortField) {
                case "code":
                    wrapper.orderBy(true, !isDesc, ErpCustomer::getCode);
                    break;
                case "name":
                    wrapper.orderBy(true, !isDesc, ErpCustomer::getName);
                    break;
                case "type":
                    wrapper.orderBy(true, !isDesc, ErpCustomer::getType);
                    break;
                case "level":
                    wrapper.orderBy(true, !isDesc, ErpCustomer::getLevel);
                    break;
                case "status":
                    wrapper.orderBy(true, !isDesc, ErpCustomer::getStatus);
                    break;
                case "createTime":
                    wrapper.orderBy(true, !isDesc, ErpCustomer::getCreateTime);
                    break;
                case "updateTime":
                    wrapper.orderBy(true, !isDesc, ErpCustomer::getUpdateTime);
                    break;
                default:
                    wrapper.orderByDesc(ErpCustomer::getCreateTime);
                    break;
            }
        } else {
            // 默认按创建时间倒序
            wrapper.orderByDesc(ErpCustomer::getCreateTime);
        }

        Page<ErpCustomer> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        Page<ErpCustomer> result = this.page(page, wrapper);

        return PageResult.of(result);
    }

}
