package com.example.public_transportation_query_system.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.service.impl.RouteServiceImpl;
import com.example.public_transportation_query_system.service.impl.StationServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "8-工具 api", description = "工具 api 接口")
@RestController
@RequestMapping("/api/util")
public class UtilController {

    @Autowired
    RouteServiceImpl routeServiceImpl;

    @Autowired
    StationServiceImpl stationServiceImpl;

    @Operation(summary = "测试 api 是否连通", description = "测试 api 是否连通")
    @GetMapping("/connected")
    public Result<Object> connected() {
        return Result.success(true);
    }

    @Operation(summary = "搜索", description = "搜索接口")
    @GetMapping("/search")
    public Result<Object> search(BasePageQuery query) {
        // 表单验证
        if (StringUtils.isBlank(query.getQuery())) {
            return Result.failure("未输入任何要查询的内容");
        }

        Result<Object> routePage = routeServiceImpl.getRoutePage(query);
        Result<Object> stationPage = stationServiceImpl.getStationPage(query);

        HashMap<String, Object> map = new HashMap<>();

        map.put("routes", routePage.data());
        map.put("stations", stationPage.data());

        return Result.success(map);
    }

}
