package com.yummyerp.cloud.modules.common.result;

import lombok.Getter;

/**
 * 枚举了一些常用API操作码
 */
@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");

    private final Integer code;
    private final String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}