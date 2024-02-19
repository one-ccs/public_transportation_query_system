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

@Tag(name = "6-公告", description = "公告接口")
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Operation(summary = "获取公告列表", description = "获取公告列表接口")
    @GetMapping
    public Result<Object> apiNoticeGet(QueryNoticeVO queryNoticeVO) {
        System.out.println(queryNoticeVO);
        return Result.success(queryNoticeVO);
    }

    @Operation(summary = "添加公告", description = "添加公告接口")
    @PutMapping
    public Result<Object> apiNoticePut() {
        return Result.success();
    }

    @Operation(summary = "修改公告", description = "修改公告接口")
    @PostMapping
    public Result<Object> apiNoticePost() {
        return Result.success();
    }

    @Operation(summary = "删除公告", description = "删除公告接口")
    @DeleteMapping
    public Result<Object> apiNoticeDelete() {
        return Result.success();
    }
}
