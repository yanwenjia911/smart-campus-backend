package com.campus.common.result;

import lombok.Data;
import java.io.Serializable;

/**
 * 通用返回结果封装
 * @param <T> 数据类型
 */
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码：0成功，1失败
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    private R() {
    }

    /**
     * 成功（无数据）
     */
    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.code = 0;
        r.message = "success";
        return r;
    }

    /**
     * 成功（带数据）
     */
    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.code = 0;
        r.message = "success";
        r.data = data;
        return r;
    }

    /**
     * 成功（带数据+自定义消息）⭐解决你当前NoticeController报错
     */
    public static <T> R<T> ok(T data, String message) {
        R<T> r = new R<>();
        r.code = 0;
        r.message = message;
        r.data = data;
        return r;
    }

    /**
     * 成功（仅消息）
     */
    public static <T> R<T> ok(String message) {
        R<T> r = new R<>();
        r.code = 0;
        r.message = message;
        return r;
    }

    /**
     * 失败
     */
    public static <T> R<T> error(String message) {
        R<T> r = new R<>();
        r.code = 1;
        r.message = message;
        return r;
    }

    /**
     * 失败（自定义状态码）
     */
    public static <T> R<T> error(Integer code, String message) {
        R<T> r = new R<>();
        r.code = code;
        r.message = message;
        return r;
    }
}