package com.example.public_transportation_query_system.controller.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.public_transportation_query_system.entity.vo.Result;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于接口参数校验处理的控制器
 */
@Slf4j
@RestControllerAdvice
public class ValidationController {

    /**
     * 与 SpringBoot 保持一致，校验不通过打印警告信息，而不是直接抛出异常
     * @param exception 验证异常
     * @return 校验结果
     */
    @ExceptionHandler(ValidationException.class)
    public Result<Void> validateError(ValidationException exception) {
        log.warn("Resolved [{}: {}]", exception.getClass().getName(), exception.getMessage());

        return Result.failure("请求参数有误");
    }
}
