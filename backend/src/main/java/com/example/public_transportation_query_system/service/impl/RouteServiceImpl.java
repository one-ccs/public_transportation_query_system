package com.example.public_transportation_query_system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.bo.RouteBO;
import com.example.public_transportation_query_system.entity.bo.StationBO;
import com.example.public_transportation_query_system.entity.po.Route;
import com.example.public_transportation_query_system.entity.vo.BasePageQuery;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.request.DeleteVO;
import com.example.public_transportation_query_system.entity.vo.request.RouteVO;
import com.example.public_transportation_query_system.mapper.RouteMapper;
import com.example.public_transportation_query_system.mapper.StationMapper;
import com.example.public_transportation_query_system.service.IRouteService;

@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements IRouteService {

    @Autowired
    RouteStationServiceImpl routeStationServiceImpl;

    @Autowired
    StationMapper stationMapper;

    @Autowired
    RouteMapper routeMapper;

    /**
     * 返回经过某站点的所有线路（包含站点列表）
     * @param stationId
     * @return
     */
    public List<RouteBO> routesOfStation(Integer stationId) {
        List<Route> routes = routeMapper.routesOfStation(stationId);

        return routes.stream()
            .map(this::getRouteBO)
            .toList();
    }

    /**
     * 返回包含站点列表的线路
     * @param route
     * @return
     */
    public RouteBO getRouteBO(Route route) {
        return route.asViewObject(RouteBO.class, v -> {
            v.setStations(this.stationsOfRoute(route.getId()));
        });
    }

    /**
     * 返回线路中包含的所有站点
     * @param routeId 线路 id
     * @return
     */
    public List<StationBO> stationsOfRoute(Integer routeId) {
        return stationMapper.stationsOfRoute(routeId);
    }

    public Result<Object> getRoutePage(BasePageQuery query) {
        // 构造查询条件
        LambdaQueryWrapper<Route> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
            .ge(query.getStartDatetime() != null, Route::getOpeningDatetime, query.getStartDatetime())
            .le(query.getEndDatetime() != null, Route::getOpeningDatetime, query.getEndDatetime())
            .like(StringUtils.isNotBlank(query.getQuery()), Route::getNo, query.getQuery());

        // 分页
        Page<Route> page = new Page<>(
            Optional.ofNullable(query.getPageIndex()).orElse(1),
            Optional.ofNullable(query.getPageSize()).orElse(10)
        );

        // 获取分页数据
        this.page(page, queryWrapper);

        // 构造返回结构
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", page.getTotal());
        map.put("finished", !page.hasNext());
        map.put("list", page.getRecords().stream()
            .map(this::getRouteBO)
            .toList()
        );

        return Result.success("查询成功", map);
    }

    public Result<Object> addRoute(RouteVO routeVo) {
        // 表单验证
        if (StringUtils.isBlank(routeVo.getNo())) {
            return Result.failure("线路号不能为空");
        }
        if (this.lambdaQuery().eq(Route::getNo, routeVo.getNo()).one() != null) {
            return Result.failure("线路号 '" + routeVo.getNo() + "' 重复");
        }
        for (StationBO station : routeVo.getStations()) {
            if (ObjectUtils.isEmpty(station.getSequence())) {
                return Result.failure("站点 sequence 属性不能为空");
            }
        }

        // 构造新线路并清除 id
        Route newRoute = routeVo.asViewObject(Route.class, v -> v
            .setId(null)
        );

        // 添加线路
        if (this.save(newRoute)) {
            // 设置新线路的 id
            routeVo.setId(newRoute.getId());
            // 添加站点
            if (routeVo.getRouteStations().size() > 0 &&
                !routeStationServiceImpl.saveBatch(routeVo.getRouteStations())
            ) {
                return Result.failure("添加失败，线路途径站点添加失败");
            }
            return Result.success("添加成功");

        }
        return Result.failure("添加失败");
    }

    public Result<Object> modifyRoute(RouteVO routeVo) {
        // 表单验证
        if (routeVo.getId() == null) {
            return Result.failure("线路 id 不能为空");
        }
        for (StationBO station : routeVo.getStations()) {
            if (ObjectUtils.isEmpty(station.getSequence())) {
                return Result.failure("站点 sequence 属性不能为空");
            }
        }

        Route route = routeVo.asViewObject(Route.class);

        // 修改线路
        if (this.updateById(route)) {
            // 删除旧站点信息
            routeStationServiceImpl.deleteAllByRouteId(route.getId());
            // 添加新站点信息
            if (routeStationServiceImpl.saveBatch(routeVo.getRouteStations())) {
                return Result.success("修改成功");
            }
            return Result.failure("修改失败， 修改线路途径站点失败");
        }
        return Result.failure("修改失败");
    }

    public Result<Object> deleteRoute(DeleteVO deleteVO) {
        if (deleteVO.getIds() != null) {
            // 批量删除
            if (this.removeBatchByIds(deleteVO.getIds())) {
                return Result.success("批量删除成功");
            }
            return Result.failure("批量删除失败");
        }
        else if (deleteVO.getId() != null) {
            // 单个删除
            if (this.removeById(deleteVO.getId())) {
                return Result.success("删除成功");
            }
            return Result.failure("删除失败");
        }
        return Result.failure("删除失败，参数 id 和 ids 不能同时为空");
    }

    public Result<Object> getRouteDetail(Integer id) {
        if (id == null) {
            return Result.failure("线路 id 不能为空");
        }
        Route route = this.lambdaQuery().eq(Route::getId, id).one();

        RouteBO routeBO = this.getRouteBO(route);

        return Result.success("线路详情", routeBO);
    }

}
