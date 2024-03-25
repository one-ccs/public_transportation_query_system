package com.example.public_transportation_query_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.po.Route;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.service.impl.RouteServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "3-线路信息", description = "线路信息接口")
@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    RouteServiceImpl routeServiceImpl;

    @Operation(summary = "获取线路信息列表", description = "获取线路信息列表接口")
    @GetMapping
    public Result<Object> apiRouteGet(BasePageQuery query) {
        return routeServiceImpl.getPageRoute(query);
    }

    @Operation(summary = "添加线路信息", description = "添加线路信息接口")
    @PutMapping
    public Result<Object> apiRoutePut(@RequestBody Route route) {
        return routeServiceImpl.addRoute(route);
    }

    @Operation(summary = "修改线路信息", description = "修改线路信息接口")
    @PostMapping
    public Result<Object> apiRoutePost(@RequestBody Route route) {
        return routeServiceImpl.modifyRoute(route);
    }

    @Operation(summary = "删除线路信息", description = "删除线路信息接口")
    @DeleteMapping
    public Result<Object> apiRouteDelete(@RequestBody DeleteVO deleteVO) {
        return routeServiceImpl.deleteRoute(deleteVO);
    }
}
