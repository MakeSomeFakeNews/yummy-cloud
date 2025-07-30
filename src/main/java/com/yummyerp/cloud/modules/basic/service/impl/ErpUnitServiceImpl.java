package com.yummyerp.cloud.modules.basic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yummyerp.cloud.modules.basic.dto.ErpUnitQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpUnit;
import com.yummyerp.cloud.modules.basic.mapper.ErpUnitMapper;
import com.yummyerp.cloud.modules.basic.service.ErpUnitService;
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
 * 计量单位表 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
public class ErpUnitServiceImpl extends ServiceImpl<ErpUnitMapper, ErpUnit> implements ErpUnitService {

    @Override
    public PageResult<ErpUnit> getUnitPageList(PageRequest pageRequest, ErpUnitQuery query) {
        LambdaQueryWrapper<ErpUnit> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(query.getCode())) {
            wrapper.like(ErpUnit::getCode, query.getCode());
        }
        if (StringUtils.hasText(query.getName())) {
            wrapper.like(ErpUnit::getName, query.getName());
        }
        if (StringUtils.hasText(query.getSymbol())) {
            wrapper.like(ErpUnit::getSymbol, query.getSymbol());
        }
        if (query.getType() != null) {
            wrapper.eq(ErpUnit::getType, query.getType());
        }
        if (query.getStatus() != null) {
            wrapper.eq(ErpUnit::getStatus, query.getStatus());
        }

        // 动态排序处理
        if (StringUtils.hasText(pageRequest.getSortField())) {
            String sortField = pageRequest.getSortField();
            boolean isDesc = pageRequest.isDesc();
            
            switch (sortField) {
                case "code":
                    wrapper.orderBy(true, !isDesc, ErpUnit::getCode);
                    break;
                case "name":
                    wrapper.orderBy(true, !isDesc, ErpUnit::getName);
                    break;
                case "symbol":
                    wrapper.orderBy(true, !isDesc, ErpUnit::getSymbol);
                    break;
                case "type":
                    wrapper.orderBy(true, !isDesc, ErpUnit::getType);
                    break;
                case "status":
                    wrapper.orderBy(true, !isDesc, ErpUnit::getStatus);
                    break;
                case "createTime":
                    wrapper.orderBy(true, !isDesc, ErpUnit::getCreateTime);
                    break;
                case "updateTime":
                    wrapper.orderBy(true, !isDesc, ErpUnit::getUpdateTime);
                    break;
                default:
                    wrapper.orderByDesc(ErpUnit::getCreateTime);
                    break;
            }
        } else {
            // 默认按创建时间倒序
            wrapper.orderByDesc(ErpUnit::getCreateTime);
        }

        Page<ErpUnit> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        Page<ErpUnit> result = this.page(page, wrapper);

        return PageResult.of(result);
    }


}
