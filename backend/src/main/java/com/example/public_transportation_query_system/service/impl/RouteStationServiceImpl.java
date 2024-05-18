package com.example.public_transportation_query_system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.RouteStation;
import com.example.public_transportation_query_system.mapper.RouteStationMapper;
import com.example.public_transportation_query_system.service.IRouteStationService;

@Service
public class RouteStationServiceImpl extends ServiceImpl<RouteStationMapper, RouteStation> implements IRouteStationService {

    @Autowired
    RouteStationMapper routeStationMapper;

    public int deleteAllByRouteId(Integer routeId) {
        return routeStationMapper.deleteAllByRouteId(routeId);
    }

}
