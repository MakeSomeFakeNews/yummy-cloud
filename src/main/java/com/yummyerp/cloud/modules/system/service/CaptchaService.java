package com.yummyerp.cloud.modules.system.service;

import com.yummyerp.cloud.modules.system.dto.CaptchaResponse;

/**
 * 验证码服务接口
 */
public interface CaptchaService {

    /**
     * 生成验证码
     */
    CaptchaResponse generateCaptcha();

    /**
     * 验证验证码
     */
    boolean verifyCaptcha(String captchaId, String captcha);

    /**
     * 删除验证码
     */
    void deleteCaptcha(String captchaId);
}