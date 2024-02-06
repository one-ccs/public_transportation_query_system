package com.example.publictransportationquerysystem.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.publictransportationquerysystem.entity.po.Station;
import com.example.publictransportationquerysystem.mapper.StationMapper;
import com.example.publictransportationquerysystem.service.IStationService;

@Service
public class StationServiceImpl extends ServiceImpl<StationMapper, Station> implements IStationService {

}
