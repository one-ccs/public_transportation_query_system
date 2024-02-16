package com.example.public_transportation_query_system.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.vo.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "1-用户", description = "用户接口")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Operation(summary = "注册", description = "用户注册接口")
    @PostMapping("/register")
    public Result<Object> register(String username, String password, @RequestParam(name = "email", defaultValue = "") String email) {

        return Result.success("注册成功");
    }
}
