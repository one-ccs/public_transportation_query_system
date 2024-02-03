package com.example.publictransportationquerysystem.common;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;

public record Result<T>(Integer code, String message, T data) {

    // 快速返回操作成功的响应结果 (带响应数据)
    public static <T> Result<T> success(T data) {
        return new Result<>(0, "操作成功", data);
    }

    // 快速返回操作成功的响应结果
    public static <T> Result<T> success() {
        return new Result<>(0, "操作成功", null);
    }

    // 快速返回操作失败的响应结果
    public static <T> Result<T> error(T data) {
        return new Result<>(1, "操作失败", data);
    }

    // 快速返回操作失败的响应结果
    public static <T> Result<T> error(String message) {
        return new Result<>(1, message, null);
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}
