package com.yummyerp.cloud.modules.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yummyerp.cloud.modules.basic.dto.ErpCustomerQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpCustomer;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;

/**
 * <p>
 * 客户信息表 服务类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
public interface ErpCustomerService extends IService<ErpCustomer> {

    /**
     * 获取客户分页列表
     */
    PageResult<ErpCustomer> getCustomerPageList(PageRequest pageRequest, ErpCustomerQuery query);

}
