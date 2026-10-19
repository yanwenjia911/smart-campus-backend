package com.campus.common.exception;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 仅消息构造
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    /**
     * 状态码 + 消息构造
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 获取状态码
     */
    public Integer getCode() {
        return code;
    }
}