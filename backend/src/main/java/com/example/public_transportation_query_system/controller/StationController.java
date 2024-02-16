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

@Tag(name = "3-站点信息", description = "站点信息接口")
@Controller
@RequestMapping("/api/station")
public class StationController {

    @Operation(summary = "获取站点列表", description = "获取站点列表接口")
    @GetMapping
    public Result<Object> apiStationGet() {
        return Result.success();
    }

    @Operation(summary = "添加站点", description = "添加站点接口")
    @PutMapping
    public Result<Object> apiStationPut() {
        return Result.success();
    }

    @Operation(summary = "修改站点", description = "修改站点接口")
    @PostMapping
    public Result<Object> apiStationPost() {
        return Result.success();
    }

    @Operation(summary = "删除站点", description = "删除站点接口")
    @DeleteMapping
    public Result<Object> apiStationDelete() {
        return Result.success();
    }
}
