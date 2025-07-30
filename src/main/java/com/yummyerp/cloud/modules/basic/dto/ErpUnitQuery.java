package com.yummyerp.cloud.modules.basic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 计量单位查询条件
 *
 * @author 周欢
 * @since 2025-07-30
 */
@Data
@ApiModel("计量单位查询条件")
public class ErpUnitQuery {

    @ApiModelProperty("单位编码")
    private String code;

    @ApiModelProperty("单位名称")
    private String name;

    @ApiModelProperty("单位符号")
    private String symbol;

    @ApiModelProperty("单位类型：1-基本单位 2-长度 3-重量 4-体积 5-面积")
    @Min(value = 1, message = "单位类型值不正确")
    @Max(value = 5, message = "单位类型值不正确")
    private Integer type;


    @ApiModelProperty("状态：0-禁用 1-正常")
    @Min(value = 0, message = "状态值不正确")
    @Max(value = 1, message = "状态值不正确")
    private Integer status;
}