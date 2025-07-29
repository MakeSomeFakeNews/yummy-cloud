package com.yummyerp.cloud.modules.basic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 供应商查询条件
 *
 * @author 周欢
 * @since 2025-07-29
 */
@Data
@ApiModel("供应商查询条件")
public class ErpSupplierQuery {

    @ApiModelProperty("供应商编码")
    private String code;

    @ApiModelProperty("供应商名称")
    private String name;

    @ApiModelProperty("供应商类型：1-生产商 2-贸易商 3-服务商")
    @Min(value = 1, message = "供应商类型值不正确")
    @Max(value = 3, message = "供应商类型值不正确")
    private Integer type;

    @ApiModelProperty("供应商级别：1-A级 2-B级 3-C级")
    @Min(value = 1, message = "供应商级别值不正确")
    @Max(value = 3, message = "供应商级别值不正确")
    private Integer level;

    @ApiModelProperty("联系人")
    private String contactPerson;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("状态：0-禁用 1-正常")
    @Min(value = 0, message = "状态值不正确")
    @Max(value = 1, message = "状态值不正确")
    private Integer status;
} 