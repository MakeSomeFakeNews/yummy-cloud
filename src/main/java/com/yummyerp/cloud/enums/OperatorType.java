package com.yummyerp.cloud.enums;

/**
 * 操作人类别
 */
public enum OperatorType {
    /**
     * 后台用户
     */
    MANAGE(0),

    /**
     * 手机端用户
     */
    MOBILE(1),

    /**
     * 其它
     */
    OTHER(2);

    private final int value;

    OperatorType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
} 