package com.yummyerp.cloud.modules.basic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 库位查询条件
 *
 * @author 周欢
 * @since 2025-07-30
 */
@Data
@ApiModel("库位查询条件")
public class ErpWarehouseLocationQuery {

    @ApiModelProperty("仓库ID")
    private Long warehouseId;

    @ApiModelProperty("库位编码")
    private String code;

    @ApiModelProperty("库位名称")
    private String name;

    @ApiModelProperty("库位类型：1-普通库位 2-冷藏库位 3-危险品库位")
    @Min(value = 1, message = "库位类型值不正确")
    @Max(value = 3, message = "库位类型值不正确")
    private Integer type;

    @ApiModelProperty("状态：0-禁用 1-正常")
    @Min(value = 0, message = "状态值不正确")
    @Max(value = 1, message = "状态值不正确")
    private Integer status;

    @ApiModelProperty("排序字段")
    private String sortField;

    @ApiModelProperty("排序方向（asc/desc）")
    private String sortOrder;
}