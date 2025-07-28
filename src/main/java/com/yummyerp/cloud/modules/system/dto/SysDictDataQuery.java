package com.yummyerp.cloud.modules.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 系统字典数据查询条件
 *
 * @author 周欢
 * @since 2025-07-28
 */
@Data
@ApiModel("系统字典数据查询条件")
public class SysDictDataQuery {

    @ApiModelProperty("字典标签")
    private String label;

    @ApiModelProperty("字典键值")
    private String value;

    @ApiModelProperty("字典状态 (0正常 1停用)")
    @Min(value = 0, message = "状态值不正确")
    @Max(value = 1, message = "状态值不正确")
    private Integer status;

    @ApiModelProperty("字典类型")
    private String dictType;
}