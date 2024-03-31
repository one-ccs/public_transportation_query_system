package com.example.public_transportation_query_system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.public_transportation_query_system.entity.bo.StationBO;
import com.example.public_transportation_query_system.entity.po.Station;

@Mapper
public interface StationMapper extends BaseMapper<Station> {

    List<StationBO> getStationsByRouteId(Integer routeId);

}
