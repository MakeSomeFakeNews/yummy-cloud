package com.yummyerp.cloud.modules.system.service.impl;

import com.yummyerp.cloud.modules.system.dto.CaptchaResponse;
import com.yummyerp.cloud.modules.system.service.CaptchaService;
import com.yummyerp.cloud.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现类
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String CAPTCHA_PREFIX = "captcha:";
    private static final int CAPTCHA_EXPIRE_MINUTES = 5;

    @Override
    public CaptchaResponse generateCaptcha() {
        // 生成验证码
        CaptchaUtil.CaptchaResult captchaResult = CaptchaUtil.generateCaptcha();

        // 生成验证码ID
        String captchaId = UUID.randomUUID().toString();

        // 存储验证码到Redis（不区分大小写）
        redisTemplate.opsForValue().set(
            CAPTCHA_PREFIX + captchaId,
            captchaResult.getCode().toLowerCase(),
            CAPTCHA_EXPIRE_MINUTES,
            TimeUnit.MINUTES
        );

        return new CaptchaResponse(captchaId, captchaResult.getImage());
    }

    @Override
    public boolean verifyCaptcha(String captchaId, String captcha) {
        String storedCaptcha = redisTemplate.opsForValue().get(CAPTCHA_PREFIX + captchaId);
        if (storedCaptcha == null) {
            return false;
        }
        return storedCaptcha.equals(captcha.toLowerCase());
    }

    @Override
    public void deleteCaptcha(String captchaId) {
        redisTemplate.delete(CAPTCHA_PREFIX + captchaId);
    }
}