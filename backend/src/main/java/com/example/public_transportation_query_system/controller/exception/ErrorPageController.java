package com.example.public_transportation_query_system.controller.exception;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.vo.Result;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 专门用于处理错误页面的控制器
 */
@RestController
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class ErrorPageController extends AbstractErrorController {

    public ErrorPageController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * 所有错误在这里统一处理，自动解析状态码和原因
     * @param request 请求
     * @return 失败响应
     */
    @RequestMapping
    public Result<Void> error(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = this.getStatus(request);
        Map<String, Object> errorAttributes = this.getErrorAttributes(request, this.getAttributeOptions());
        String message = this.convertErrorMessage(status).orElse(errorAttributes.get("message").toString());

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        return Result.failure(status.value(), message);
    }

    /**
     * 对于一些特殊的状态码，错误信息转换
     * @param status 状态码
     * @return 错误信息
     */
    private Optional<String> convertErrorMessage(HttpStatus status){
        String value = switch (status.value()) {
            case 400 -> "请求的参数有误";
            case 404 -> "请求的接口不存在";
            case 405 -> "请求的方法有误";
            case 500 -> "内部错误，请联系管理员";
            default -> null;
        };
        return Optional.ofNullable(value);
    }

    /**
     * 错误属性获取选项，这里额外添加了错误消息和异常类型
     * @return 选项
     */
    private ErrorAttributeOptions getAttributeOptions(){
        return ErrorAttributeOptions
                .defaults()
                .including(ErrorAttributeOptions.Include.MESSAGE,
                        ErrorAttributeOptions.Include.EXCEPTION);
    }
}
