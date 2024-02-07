package com.example.public_transportation_query_system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.Station;
import com.example.public_transportation_query_system.mapper.StationMapper;
import com.example.public_transportation_query_system.service.IStationService;

@Service
public class StationServiceImpl extends ServiceImpl<StationMapper, Station> implements IStationService {

}
