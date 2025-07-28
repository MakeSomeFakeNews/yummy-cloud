package com.yummyerp.cloud.modules.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 系统部门查询条件
 *
 * @author 周欢
 * @since 2025-07-28
 */
@Data
@ApiModel("系统部门查询条件")
public class SysDeptQuery {

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("部门状态 (0正常 1停用)")
    @Min(value = 0, message = "状态值不正确")
    @Max(value = 1, message = "状态值不正确")
    private Integer status;

    @ApiModelProperty("上级部门ID")
    private Long parentId;
}