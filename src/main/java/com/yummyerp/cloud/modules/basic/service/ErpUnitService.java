package com.yummyerp.cloud.modules.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yummyerp.cloud.modules.basic.dto.ErpUnitQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpUnit;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 计量单位表 服务类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
public interface ErpUnitService extends IService<ErpUnit> {

    /**
     * 获取计量单位分页列表
     */
    PageResult<ErpUnit> getUnitPageList(PageRequest pageRequest, ErpUnitQuery query);

}
