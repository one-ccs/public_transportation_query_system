package com.example.public_transportation_query_system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.public_transportation_query_system.entity.po.RouteStation;

@Mapper
public interface RouteStationMapper extends BaseMapper<RouteStation> {

}
