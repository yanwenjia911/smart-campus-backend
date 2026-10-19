package com.campus.common.constant;

/**
 * HTTP 状态码常量
 */
public class HttpStatus {

    /**
     * 成功
     */
    public static final int SUCCESS = 200;

    /**
     * 失败
     */
    public static final int ERROR = 500;

    /**
     * 未授权（未登录）
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 禁止访问（无权限）
     */
    public static final int FORBIDDEN = 403;

    /**
     * 资源不存在
     */
    public static final int NOT_FOUND = 404;

    /**
     * 参数错误
     */
    public static final int BAD_REQUEST = 400;

    private HttpStatus() {
    }
}