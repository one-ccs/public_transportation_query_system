package com.example.public_transportation_query_system.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.public_transportation_query_system.entity.vo.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.stereotype.Controller;

@Tag(name = "4-失物招领", description = "失物招领接口")
@Controller
@RequestMapping("/api/lost")
public class LostController {

    @Operation(summary = "获取失物招领列表", description = "获取失物招领列表接口")
    @GetMapping
    public Result<Object> apiLostGet() {
        return Result.success();
    }

    @Operation(summary = "添加失物招领", description = "添加失物招领接口")
    @PutMapping
    public Result<Object> apiLostPut() {
        return Result.success();
    }

    @Operation(summary = "修改失物招领", description = "修改失物招领接口")
    @PostMapping
    public Result<Object> apiLostPost() {
        return Result.success();
    }

    @Operation(summary = "删除失物招领", description = "删除失物招领接口")
    @DeleteMapping
    public Result<Object> apiLostDelete() {
        return Result.success();
    }
}
