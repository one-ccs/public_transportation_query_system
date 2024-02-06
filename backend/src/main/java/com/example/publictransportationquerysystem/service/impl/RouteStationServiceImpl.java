package com.example.publictransportationquerysystem.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.publictransportationquerysystem.entity.po.RouteStation;
import com.example.publictransportationquerysystem.mapper.RouteStationMapper;
import com.example.publictransportationquerysystem.service.IRouteStationService;

@Service
public class RouteStationServiceImpl extends ServiceImpl<RouteStationMapper, RouteStation> implements IRouteStationService {

}
