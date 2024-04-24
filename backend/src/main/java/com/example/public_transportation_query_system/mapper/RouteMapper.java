package com.example.public_transportation_query_system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.public_transportation_query_system.entity.po.Route;

@Mapper
public interface RouteMapper extends BaseMapper<Route> {
    /**
     * 查询经过某站点的所有线路
     * @param stationId 站点 id
     * @return
     */
    List<Route> routesOfStation(Integer stationId);
}
