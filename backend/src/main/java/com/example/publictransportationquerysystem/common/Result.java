package com.example.publictransportationquerysystem.common;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

public record Result<T>(Integer code, String message, T data) {

    /**
     * 成功（带数据）
     * @param String
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(0, "操作成功", data);
    }

    /**
     * 成功
     * @return Result
     */
    public static <T> Result<T> success() {
        return new Result<>(0, "操作成功", null);
    }

    /**
     * 未登录
     * @param String
     * @return Result
     */
    public static <T> Result<T> unauthorized() {
        return new Result<T>(401, "请登录后操作", null);
    }

    /**
     * 无权访问
     * @param String
     * @return Result
     */
    public static <T> Result<T> forbidden() {
        return new Result<T>(403, "您无权操作", null);
    }

    /**
     * 失败（带数据）
     * @param data<T>
     * @return Result
     */
    public static <T> Result<T> error(T data) {
        return new Result<>(1, "操作失败", data);
    }

    /**
     * 失败（带提示信息）
     * @param String
     * @return Result
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(1, message, null);
    }

    /**
     * 将 Result 转为 String Json
     * @return String
     */
    public String toJsonString() {
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}
