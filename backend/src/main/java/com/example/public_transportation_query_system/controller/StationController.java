package com.example.public_transportation_query_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.public_transportation_query_system.entity.po.Station;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.service.impl.StationServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "4-站点", description = "站点接口")
@RestController
@RequestMapping("/api/station")
public class StationController {

    @Autowired
    StationServiceImpl stationServiceImpl;

    @Operation(summary = "获取站点信息", description = "获取站点信息接口")
    @GetMapping
    public Result<Object> get(Integer id) {
        return Result.success(stationServiceImpl.getById(id));
    }

    @Operation(summary = "添加站点", description = "添加站点接口")
    @PutMapping
    public Result<Object> put(@RequestBody Station station) {
        return stationServiceImpl.addStation(station);
    }

    @Operation(summary = "修改站点", description = "修改站点接口")
    @PostMapping
    public Result<Object> post(@RequestBody Station station) {
        return stationServiceImpl.modifyStation(station);
    }

    @Operation(summary = "删除站点", description = "删除站点接口")
    @DeleteMapping
    public Result<Object> delete(@RequestBody DeleteVO deleteVO) {
        return stationServiceImpl.deleteStation(deleteVO);
    }

    @Operation(summary = "分页查询站点列表", description = "分页查询站点列表接口")
    @GetMapping("/pageQuery")
    public Result<Object> pageQuery(BasePageQuery query) {
        return stationServiceImpl.getStationPage(query);
    }

    @Operation(summary = "查询附近站点", description = "查询附近站点接口")
    @GetMapping("/nearby")
    public Result<Object> nearby(Double longitude, Double latitude, Double distance) {
        return stationServiceImpl.nearby(longitude, latitude, distance);
    }

    @Operation(summary = "查询经过站点的线路", description = "查询经过站点的线路接口")
    @GetMapping("/routes")
    public Result<Object> routes(Integer id) {
        return stationServiceImpl.routes(id);
    }

}
