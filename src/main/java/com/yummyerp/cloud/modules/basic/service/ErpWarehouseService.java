package com.yummyerp.cloud.modules.basic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yummyerp.cloud.modules.basic.dto.ErpWarehouseQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpWarehouse;
import com.yummyerp.cloud.modules.common.dto.PageRequest;
import com.yummyerp.cloud.modules.common.dto.PageResult;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 仓库信息表 服务类
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
public interface ErpWarehouseService extends IService<ErpWarehouse> {

    /**
     * 获取仓库分页列表
     */
    PageResult<ErpWarehouse> getWarehousePageList(PageRequest pageRequest, ErpWarehouseQuery query);

    /**
     * 获取仓库选项列表（用于下拉框）
     */
    List<Map<String, Object>> getWarehouseOptions(String keyword, Integer size);

    /**
     * 根据ID获取仓库选项（用于编辑时回显）
     */
    Map<String, Object> getWarehouseOptionById(Long id);

}
