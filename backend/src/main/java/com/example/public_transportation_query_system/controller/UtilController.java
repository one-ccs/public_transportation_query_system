package com.example.public_transportation_query_system.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.public_transportation_query_system.entity.bo.RouteBO;
import com.example.public_transportation_query_system.entity.bo.StationBO;
import com.example.public_transportation_query_system.entity.po.Route;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.response.RouteOfLength;
import com.example.public_transportation_query_system.mapper.UtilMapper;
import com.example.public_transportation_query_system.service.impl.RouteServiceImpl;
import com.example.public_transportation_query_system.service.impl.StationServiceImpl;
import com.example.public_transportation_query_system.service.impl.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "8-工具 api", description = "工具 api 接口")
@RestController
@RequestMapping("/api/util")
public class UtilController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    RouteServiceImpl routeServiceImpl;

    @Autowired
    StationServiceImpl stationServiceImpl;

    @Autowired
    UtilMapper utilMapper;

    @Operation(summary = "测试 api 是否连通", description = "测试 api 是否连通接口")
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

    @Operation(summary = "获取统计数据", description = "获取统计数据接口")
    @GetMapping("/statistics")
    public Result<Object> statistics() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("userCount", userServiceImpl.count());
        result.put("routeCount", routeServiceImpl.count());
        result.put("stationCount", stationServiceImpl.count());
        result.put("lostStatusCount", utilMapper.lostStatusCount());
        result.put("routeOfStationCount", utilMapper.routeOfStationCount());
        result.put("routeOfLengthRank", this.routeOfLengthRank());

        return Result.success(result);
    }

    public List<RouteOfLength> routeOfLengthRank() {
        List<Route> routes = routeServiceImpl.list();

        List<RouteBO> routeBOs = routes.stream().map(routeServiceImpl::getRouteBO).toList();

        List<RouteOfLength> routeOfLengths = routeBOs.stream().map(routeBO -> {
            double length = 0.0;
            StationBO previous = null;

            for (StationBO stationBO : routeBO.getStations()) {
                if (routeBO.getId() == 17) System.out.println(stationBO);
                if (previous != null) {
                    length += this.getDistanceDouble(
                        previous.getLatitude(),
                        previous.getLongitude(),
                        stationBO.getLatitude(),
                        stationBO.getLatitude()
                    );
                    if (routeBO.getId() == 17) System.out.println(length);
                }
                previous = stationBO;
            }
            System.out.println(getDistanceDouble(29.344384395150854, 106.53962875115394, 29.34655874141637, 106.53984637397708));
            return new RouteOfLength(
                routeBO.getId(),
                routeBO.getNo(),
                length
            );
        }).toList();

        return routeOfLengths;
    }

    public final double EARTH_RADIUS = 6371000.0; // 地球半径，单位米

    public double getDistanceDouble(Double lat1, Double lng1, Double lat2, Double lng2) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(lng1);// A经弧度
        double radiansAY = Math.toRadians(lat1);// A纬弧度
        double radiansBX = Math.toRadians(lng2);// B经弧度
        double radiansBY = Math.toRadians(lat2);// B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX) + Math.sin(radiansAY) * Math.sin(radiansBY);

        // log.info("cos = " + cos);//值域[-1,1]
        double acos = Math.acos(cos);// 反余弦值

        // log.info("acos = " + acos);//值域[0,π]
        // log.info("∠AOB = " + Math.toDegrees(acos));//球心角 值域[0,180]
        return EARTH_RADIUS * acos;// 最终结果
    }

}
