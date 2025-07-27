package com.yummyerp.cloud.modules.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录响应参数
 */
@Data
@ApiModel(value = "用户登录响应参数")
public class LoginResponse {

    @ApiModelProperty(value = "访问令牌")
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}