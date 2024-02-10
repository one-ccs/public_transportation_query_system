package com.example.public_transportation_query_system.entity.vo;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

public record Result<T>(Integer code, String message, T data) {

    /**
     * 成功（带数据）
     * @param data<T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功（带提示信息）
     * @param message
     * @return
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(200, message, null);
    }

    /**
     * 成功
     * @return
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 未登录
     * @return
     */
    public static <T> Result<T> unauthorized() {
        return new Result<>(401, "请登录后操作", null);
    }

    /**
     * 无权访问
     * @return
     */
    public static <T> Result<T> forbidden() {
        return new Result<>(403, "您无权操作", null);
    }

    /**
     * 失败（带状态码）
     * @param code<Integer>
     * @return
     */
    public static <T> Result<T> failure(Integer code) {
        return new Result<>(code, "操作失败", null);
    }

    /**
     * 失败（带状态码及提示信息）
     * @param code<Integer>
     * @param message<String>
     * @return
     */
    public static <T> Result<T> failure(Integer code, String message) {
        if (message == null) message = "未知错误，操作失败";
        return new Result<>(code, message, null);
    }

    /**
     * 将 Result 转为 String Json
     * @return
     */
    public String toJsonString() {
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}
