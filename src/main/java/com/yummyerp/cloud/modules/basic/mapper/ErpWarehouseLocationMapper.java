package com.yummyerp.cloud.modules.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yummyerp.cloud.modules.basic.dto.ErpWarehouseLocationQuery;
import com.yummyerp.cloud.modules.basic.entity.ErpWarehouseLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 库位信息表 Mapper 接口
 * </p>
 *
 * @author 周欢
 * @since 2025-07-27
 */
@Mapper
public interface ErpWarehouseLocationMapper extends BaseMapper<ErpWarehouseLocation> {

    /**
     * 分页查询库位列表（包含仓库名称）
     */
    Page<ErpWarehouseLocation> selectWarehouseLocationPageWithWarehouseName(
            Page<ErpWarehouseLocation> page,
            @Param("query") ErpWarehouseLocationQuery query);

}
