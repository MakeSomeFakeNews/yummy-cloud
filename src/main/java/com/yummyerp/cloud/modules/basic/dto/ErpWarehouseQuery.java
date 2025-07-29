package com.yummyerp.cloud.modules.basic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 仓库查询条件
 *
 * @author 周欢
 * @since 2025-07-29
 */
@Data
@ApiModel("仓库查询条件")
public class ErpWarehouseQuery {

    @ApiModelProperty("仓库编码")
    private String code;

    @ApiModelProperty("仓库名称")
    private String name;

    @ApiModelProperty("仓库类型：1-原料仓 2-成品仓 3-半成品仓 4-次品仓")
    @Min(value = 1, message = "仓库类型值不正确")
    @Max(value = 4, message = "仓库类型值不正确")
    private Integer type;

    @ApiModelProperty("仓库管理员")
    private String manager;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("状态：0-禁用 1-正常")
    @Min(value = 0, message = "状态值不正确")
    @Max(value = 1, message = "状态值不正确")
    private Integer status;
} 