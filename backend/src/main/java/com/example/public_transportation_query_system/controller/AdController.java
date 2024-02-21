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

@Tag(name = "7-广告", description = "广告接口")
@RestController
@RequestMapping("/api/ad")
public class AdController {

    @Operation(summary = "获取广告列表", description = "获取广告列表接口")
    @GetMapping
    public Result<Object> apiNoticeGet(QueryNoticeVO queryNoticeVO) {
        return Result.success(queryNoticeVO);
    }

    @Operation(summary = "添加广告", description = "添加广告接口")
    @PutMapping
    public Result<Object> apiNoticePut() {
        return Result.success();
    }

    @Operation(summary = "修改广告", description = "修改广告接口")
    @PostMapping
    public Result<Object> apiNoticePost() {
        return Result.success();
    }

    @Operation(summary = "删除广告", description = "删除广告接口")
    @DeleteMapping
    public Result<Object> apiNoticeDelete() {
        return Result.success();
    }
}
