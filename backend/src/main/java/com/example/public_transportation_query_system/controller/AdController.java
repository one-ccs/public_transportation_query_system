package com.example.public_transportation_query_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.po.Ad;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.service.impl.AdServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "7-广告", description = "广告接口")
@RestController
@RequestMapping("/api/ad")
public class AdController {

    @Autowired
    AdServiceImpl adServiceImpl;

    @Operation(summary = "获取广告信息", description = "获取广告信息接口")
    @GetMapping
    public Result<Object> get(Integer id) {
        return Result.success(adServiceImpl.getById(id));
    }

    @Operation(summary = "添加广告", description = "添加广告接口")
    @PutMapping
    public Result<Object> put(@RequestBody Ad ad) {
        return adServiceImpl.addAd(ad);
    }

    @Operation(summary = "修改广告", description = "修改广告接口")
    @PostMapping
    public Result<Object> post(@RequestBody Ad ad) {
        return adServiceImpl.modifyAd(ad);
    }

    @Operation(summary = "删除广告", description = "删除广告接口")
    @DeleteMapping
    public Result<Object> delete(@RequestBody DeleteVO deleteVO) {
        return adServiceImpl.deleteAd(deleteVO);
    }

    @Operation(summary = "分页查询广告列表", description = "分页查询广告列表接口")
    @GetMapping("/pageQuery")
    public Result<Object> pageQuery(BasePageQuery query) {
        return adServiceImpl.getAdPage(query);
    }
}
