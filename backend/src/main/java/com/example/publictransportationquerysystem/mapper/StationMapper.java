package com.example.publictransportationquerysystem.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.publictransportationquerysystem.entity.po.Station;

@Mapper
public interface StationMapper extends BaseMapper<Station> {

}
