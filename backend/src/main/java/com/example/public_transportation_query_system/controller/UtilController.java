package com.example.public_transportation_query_system.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.example.public_transportation_query_system.entity.vo.response.RoutePlanning;
import com.example.public_transportation_query_system.mapper.UtilMapper;
import com.example.public_transportation_query_system.service.impl.RouteServiceImpl;
import com.example.public_transportation_query_system.service.impl.StationServiceImpl;
import com.example.public_transportation_query_system.service.impl.UserServiceImpl;
import com.example.public_transportation_query_system.util.PositionUtil;

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

    @Operation(summary = "线路规划", description = "线路规划接口")
    @GetMapping("/routePlanning")
    public Result<Object> routePlanning() {
        List<Map<String, Object>> result = new ArrayList<>();


        Map<String, Object> f1 = new HashMap<>();
        List<RoutePlanning> p1 = new ArrayList<>();
        p1.add(new RoutePlanning("步行", "230米", "鸡公台站"));
        p1.add(new RoutePlanning("911路", "5站", "轨道鱼胡路站"));
        p1.add(new RoutePlanning("步行", "90米", "鱼胡路"));
        f1.put("name", "方案1");
        f1.put("list", p1);

        Map<String, Object> f2 = new HashMap<>();
        List<RoutePlanning> p2 = new ArrayList<>();
        p2.add(new RoutePlanning("步行", "230米", "鸡公台站"));
        p2.add(new RoutePlanning("914路", "5站", "轨道鱼胡路站"));
        p2.add(new RoutePlanning("步行", "19米", "鱼胡路"));
        f2.put("name", "方案2");
        f2.put("list", p2);

        result.add(f1);
        result.add(f2);

        return Result.success(result);
    }

    /**
     * 统计站点长度并排序
     * @return
     */
    public List<RouteOfLength> routeOfLengthRank() {
        List<Route> routes = routeServiceImpl.list();

        List<RouteBO> routeBOs = routes.stream().map(routeServiceImpl::getRouteBO).toList();

        List<RouteOfLength> routeOfLengths = routeBOs.stream().map(routeBO -> {
            double length = 0.0;
            StationBO previous = null;

            for (StationBO stationBO : routeBO.getStations()) {
                if (previous != null) {
                    length += PositionUtil.getDistanceDouble(
                        previous.getLongitude(),
                        previous.getLatitude(),
                        stationBO.getLongitude(),
                        stationBO.getLatitude()
                    );
                }
                previous = stationBO;
            }

            return new RouteOfLength(
                routeBO.getId(),
                routeBO.getNo(),
                length
            );
        }).sorted(Comparator.comparingDouble(RouteOfLength::getLength).reversed()).toList();

        return routeOfLengths;
    }

}
