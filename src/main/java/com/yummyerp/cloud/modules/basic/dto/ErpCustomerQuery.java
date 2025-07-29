package com.yummyerp.cloud.modules.basic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 客户查询条件
 *
 * @author 周欢
 * @since 2025-07-29
 */
@Data
@ApiModel("客户查询条件")
public class ErpCustomerQuery {

    @ApiModelProperty("客户编码")
    private String code;

    @ApiModelProperty("客户名称")
    private String name;

    @ApiModelProperty("客户类型：1-企业客户 2-个人客户")
    @Min(value = 1, message = "客户类型值不正确")
    @Max(value = 2, message = "客户类型值不正确")
    private Integer type;

    @ApiModelProperty("客户级别：1-VIP 2-普通 3-潜在")
    @Min(value = 1, message = "客户级别值不正确")
    @Max(value = 3, message = "客户级别值不正确")
    private Integer level;

    @ApiModelProperty("联系人")
    private String contactPerson;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("状态：0-禁用 1-正常")
    @Min(value = 0, message = "状态值不正确")
    @Max(value = 1, message = "状态值不正确")
    private Integer status;

    @ApiModelProperty("所属行业")
    private String industry;
}