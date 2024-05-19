package com.example.public_transportation_query_system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.StationRoute;
import com.example.public_transportation_query_system.mapper.StationRouteMapper;
import com.example.public_transportation_query_system.service.IStationRouteService;

@Service
public class StationRouteServiceImpl extends ServiceImpl<StationRouteMapper, StationRoute> implements IStationRouteService {

    @Autowired
    StationRouteMapper stationRouteMapper;

    public int deleteAllByStationId(Integer stationId) {
        return stationRouteMapper.deleteAllByStationId(stationId);
    }

}
