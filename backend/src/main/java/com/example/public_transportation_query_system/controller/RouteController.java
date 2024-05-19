package com.example.public_transportation_query_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.entity.vo.request.RouteVO;
import com.example.public_transportation_query_system.service.impl.RouteServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "3-线路", description = "线路接口")
@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    RouteServiceImpl routeServiceImpl;

    @Operation(summary = "获取线路信息", description = "获取线路信息接口")
    @GetMapping
    public Result<Object> get(Integer id) {
        return Result.success(routeServiceImpl.getById(id));
    }

    @Operation(summary = "添加线路", description = "添加线路接口")
    @PutMapping
    public Result<Object> put(@RequestBody RouteVO route) {
        return routeServiceImpl.addRoute(route);
    }

    @Operation(summary = "修改线路", description = "修改线路接口")
    @PostMapping
    public Result<Object> post(@RequestBody RouteVO route) {
        return routeServiceImpl.modifyRoute(route);
    }

    @Operation(summary = "删除线路", description = "删除线路接口")
    @DeleteMapping
    public Result<Object> delete(@RequestBody DeleteVO deleteVO) {
        return routeServiceImpl.deleteRoute(deleteVO);
    }

    @Operation(summary = "分页查询线路列表", description = "分页查询线路列表接口")
    @GetMapping("/pageQuery")
    public Result<Object> pageQuery(BasePageQuery query) {
        return routeServiceImpl.getRoutePage(query);
    }

    @Operation(summary = "查询线路详情", description = "查询线路详情接口")
    @GetMapping("/detail")
    public Result<Object> detail(Integer id) {
        return routeServiceImpl.getRouteDetail(id);
    }

}
