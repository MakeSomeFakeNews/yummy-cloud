package com.yummyerp.cloud.modules.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 验证码响应参数
 */
@Data
@ApiModel(value = "验证码响应参数")
public class CaptchaResponse {

    @ApiModelProperty(value = "验证码标识")
    private String captchaId;

    @ApiModelProperty(value = "验证码图片(Base64)")
    private String captchaImage;

    public CaptchaResponse(String captchaId, String captchaImage) {
        this.captchaId = captchaId;
        this.captchaImage = captchaImage;
    }
}