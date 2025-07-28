package com.yummyerp.cloud.modules.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 系统用户查询条件
 *
 * @author 周欢
 * @since 2025-07-28
 */
@Data
@ApiModel("系统用户查询条件")
public class SysUserQuery {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户状态 (0正常 1停用)")
    @Min(value = 0, message = "状态值不正确")
    @Max(value = 1, message = "状态值不正确")
    private Integer status;

    @ApiModelProperty("部门ID")
    private Long deptId;

    @ApiModelProperty("手机号码")
    private String phonenumber;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("真实姓名")
    private String realName;
}