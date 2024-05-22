package com.example.public_transportation_query_system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.public_transportation_query_system.entity.vo.response.LostStatusCount;
import com.example.public_transportation_query_system.entity.vo.response.RouteOfStationCount;

@Mapper
public interface UtilMapper {

    List<RouteOfStationCount> routeOfStationCount();

    List<LostStatusCount> lostStatusCount();

}
