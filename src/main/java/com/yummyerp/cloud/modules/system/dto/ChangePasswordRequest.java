package com.yummyerp.cloud.modules.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 修改密码请求DTO
 */
@Data
@ApiModel("修改密码请求")
public class ChangePasswordRequest {

    @ApiModelProperty(value = "当前密码", required = true)
    @NotBlank(message = "当前密码不能为空")
    private String currentPassword;

    @ApiModelProperty(value = "新密码", required = true)
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, message = "密码长度不能少于6位")
    private String newPassword;
}