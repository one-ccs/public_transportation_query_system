package com.example.public_transportation_query_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.QueryUserVO;
import com.example.public_transportation_query_system.entity.vo.request.UserVO;
import com.example.public_transportation_query_system.service.impl.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "1-用户", description = "用户接口")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Operation(summary = "注册", description = "用户注册接口")
    @PostMapping("/register")
    public Result<Object> register(String username, String password, @RequestParam(name = "email", defaultValue = "") String email) {
        if (userServiceImpl.register(username, password, email)) {
            return Result.success("注册成功");
        }
        return Result.success("注册失败");
    }

    @Operation(summary = "获取用户列表", description = "获取用户列表接口")
    @GetMapping
    public Result<Object> apiUserGet(QueryUserVO query) {
        return Result.success(userServiceImpl.getPageUsers(query));
    }

    @Operation(summary = "添加用户", description = "添加用户接口")
    @PutMapping
    public Result<Object> apiUserPut(UserVO userVO) {
        return Result.success(userServiceImpl.addUser(userVO));
    }

    @Operation(summary = "修改用户", description = "修改用户接口")
    @PostMapping
    public Result<Object> apiUserPost() {
        return Result.success();
    }

    @Operation(summary = "删除用户", description = "删除用户接口")
    @DeleteMapping
    public Result<Object> apiUserDelete() {
        return Result.success();
    }
}
