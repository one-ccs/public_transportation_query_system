package com.example.public_transportation_query_system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.public_transportation_query_system.entity.po.StationRoute;

@Mapper
public interface StationRouteMapper extends BaseMapper<StationRoute> {

    int deleteAllByStationId(Integer stationId);

}
