package com.yummyerp.cloud.modules.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yummyerp.cloud.modules.basic.dto.ErpSupplierQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpSupplier;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;

/**
 * <p>
 * 供应商信息表 服务类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
public interface ErpSupplierService extends IService<ErpSupplier> {

    /**
     * 获取供应商分页列表
     */
    PageResult<ErpSupplier> getSupplierPageList(PageRequest pageRequest, ErpSupplierQuery query);

}
