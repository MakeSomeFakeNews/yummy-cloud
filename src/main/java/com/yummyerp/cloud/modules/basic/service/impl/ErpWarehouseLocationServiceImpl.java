package com.yummyerp.cloud.modules.basic.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yummyerp.cloud.modules.basic.dto.ErpWarehouseLocationQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpWarehouseLocation;
import com.yummyerp.cloud.modules.basic.mapper.ErpWarehouseLocationMapper;
import com.yummyerp.cloud.modules.basic.service.ErpWarehouseLocationService;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 库位信息表 服务实现类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Service
public class ErpWarehouseLocationServiceImpl extends ServiceImpl<ErpWarehouseLocationMapper, ErpWarehouseLocation> implements ErpWarehouseLocationService {

    @Override
    public PageResult<ErpWarehouseLocation> getWarehouseLocationPageList(PageRequest pageRequest, ErpWarehouseLocationQuery query) {
        // 将pageRequest的排序参数传递给query
        if (StringUtils.hasText(pageRequest.getSortField())) {
            query.setSortField(pageRequest.getSortField());
            query.setSortOrder(pageRequest.getSortOrder());
        } else {
            // 设置默认排序
            query.setSortField("createTime");
            query.setSortOrder("desc");
        }
        
        Page<ErpWarehouseLocation> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        Page<ErpWarehouseLocation> result = baseMapper.selectWarehouseLocationPageWithWarehouseName(page, query);
        return PageResult.of(result);
    }

}
