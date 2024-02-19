package com.example.public_transportation_query_system.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.QueryNoticeVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "2-角色", description = "角色接口")
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Operation(summary = "获取角色列表", description = "获取角色列表接口")
    @GetMapping
    public Result<Object> apiNoticeGet(QueryNoticeVO queryNoticeVO) {
        System.out.println(queryNoticeVO);
        return Result.success(queryNoticeVO);
    }

    @Operation(summary = "添加角色", description = "添加角色接口")
    @PutMapping
    public Result<Object> apiNoticePut() {
        return Result.success();
    }

    @Operation(summary = "修改角色", description = "修改角色接口")
    @PostMapping
    public Result<Object> apiNoticePost() {
        return Result.success();
    }

    @Operation(summary = "删除角色", description = "删除角色接口")
    @DeleteMapping
    public Result<Object> apiNoticeDelete() {
        return Result.success();
    }
}
