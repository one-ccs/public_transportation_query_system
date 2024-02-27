package com.example.public_transportation_query_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.po.Notice;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.service.impl.NoticeServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "6-公告", description = "公告接口")
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    NoticeServiceImpl noticeServiceImpl;

    @Operation(summary = "获取公告列表", description = "获取公告列表接口")
    @GetMapping
    public Result<Object> apiNoticeGet(BasePageQuery query) {
        return noticeServiceImpl.getPageNotices(query);
    }

    @Operation(summary = "添加公告", description = "添加公告接口")
    @PutMapping
    public Result<Object> apiNoticePut(@RequestBody Notice notice) {
        return noticeServiceImpl.addNotice(notice);
    }

    @Operation(summary = "修改公告", description = "修改公告接口")
    @PostMapping
    public Result<Object> apiNoticePost(@RequestBody Notice notice) {
        return noticeServiceImpl.modifyNotice(notice);
    }

    @Operation(summary = "删除公告", description = "删除公告接口")
    @DeleteMapping
    public Result<Object> apiNoticeDelete(@RequestBody DeleteVO deleteVO) {
        return noticeServiceImpl.deleteNotice(deleteVO);
    }
}
