package com.example.public_transportation_query_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.public_transportation_query_system.entity.vo.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "2-线路信息", description = "线路信息接口")
@Controller
@RequestMapping("/api/route")
public class RouteController {

    @Operation(summary = "获取线路信息列表", description = "获取线路信息列表接口")
    @GetMapping
    public Result<Object> apiRouteGet() {
        return Result.success();
    }

    @Operation(summary = "添加线路信息", description = "添加线路信息接口")
    @PutMapping
    public Result<Object> apiRoutePut() {
        return Result.success();
    }

    @Operation(summary = "修改线路信息", description = "修改线路信息接口")
    @PostMapping
    public Result<Object> apiRoutePost() {
        return Result.success();
    }

    @Operation(summary = "删除线路信息", description = "删除线路信息接口")
    @DeleteMapping
    public Result<Object> apiRouteDelete() {
        return Result.success();
    }
}
