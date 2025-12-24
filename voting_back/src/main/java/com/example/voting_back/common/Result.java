package com.example.voting_back.common;

import java.io.Serializable;

/**
 * 通用操作结果类
 * 用于统一封装接口或业务操作的执行结果
 *
 * @param <T> 结果数据类型
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 操作状态码：成功
     */
    public static final int SUCCESS_CODE = 200;
    /**
     * 操作状态码：失败
     */
    public static final int ERROR_CODE = 500;
    /**
     * 操作状态码：参数错误
     */
    public static final int PARAM_ERROR_CODE = 400;
    /**
     * 操作状态码：未授权
     */
    public static final int UNAUTHORIZED_CODE = 401;
    /**
     * 操作状态码：资源不存在
     */
    public static final int NOT_FOUND_CODE = 404;

    /**
     * 状态码
     */
    private int code;

    /**
     * 操作消息
     */
    private String message;

    /**
     * 结果数据
     */
    private T data;

    /**
     * 操作时间戳
     */
    private long timestamp;

    /**
     * 构造方法
     */
    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 构造方法
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     */
    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 创建成功结果（无数据）
     *
     * @param <T> 数据类型
     * @return 成功结果对象
     */
    public static <T> Result<T> success() {
        return new Result<>(SUCCESS_CODE, "操作成功", null);
    }

    /**
     * 创建成功结果（带数据）
     *
     * @param data 结果数据
     * @param <T>  数据类型
     * @return 成功结果对象
     */
    public static <T> Result<T> success(T data) {
            return new Result<>(SUCCESS_CODE, "操作成功", data);


    }

    /**
     * 创建成功结果（自定义消息）
     *
     * @param message 消息
     * @param data    结果数据
     * @param <T>     数据类型
     * @return 成功结果对象
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(SUCCESS_CODE, message, data);
    }

    /**
     * 创建失败结果（默认消息）
     *
     * @param <T> 数据类型
     * @return 失败结果对象
     */
    public static <T> Result<T> error() {
        return new Result<>(ERROR_CODE, "操作失败", null);
    }

    /**
     * 创建失败结果（自定义消息）
     *
     * @param message 失败消息
     * @param <T>     数据类型
     * @return 失败结果对象
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(ERROR_CODE, message, null);
    }

    /**
     * 创建失败结果（自定义状态码和消息）
     *
     * @param code    状态码
     * @param message 失败消息
     * @param <T>     数据类型
     * @return 失败结果对象
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 创建参数错误结果
     *
     * @param message 参数错误消息
     * @param <T>     数据类型
     * @return 参数错误结果对象
     */
    public static <T> Result<T> paramError(String message) {
        return new Result<>(PARAM_ERROR_CODE, message, null);
    }

    /**
     * 创建未授权结果
     *
     * @param message 未授权消息
     * @param <T>     数据类型
     * @return 未授权结果对象
     */
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(UNAUTHORIZED_CODE, message, null);
    }

    /**
     * 创建资源不存在结果
     *
     * @param message 资源不存在消息
     * @param <T>     数据类型
     * @return 资源不存在结果对象
     */
    public static <T> Result<T> notFound(String message) {
        return new Result<>(NOT_FOUND_CODE, message, null);
    }

    /**
     * 判断操作是否成功
     *
     * @return true：成功，false：失败
     */
    public boolean isSuccess() {
        return this.code == SUCCESS_CODE;
    }

    // getter和setter方法
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}
