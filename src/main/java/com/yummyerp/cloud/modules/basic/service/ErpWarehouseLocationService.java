package com.yummyerp.cloud.modules.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yummyerp.cloud.modules.basic.dto.ErpWarehouseLocationQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpWarehouseLocation;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;

/**
 * <p>
 * 库位信息表 服务类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
public interface ErpWarehouseLocationService extends IService<ErpWarehouseLocation> {

    /**
     * 获取库位分页列表
     */
    PageResult<ErpWarehouseLocation> getWarehouseLocationPageList(PageRequest pageRequest, ErpWarehouseLocationQuery query);

}
