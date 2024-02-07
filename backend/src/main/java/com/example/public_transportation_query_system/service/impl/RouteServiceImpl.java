package com.example.public_transportation_query_system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.Route;
import com.example.public_transportation_query_system.mapper.RouteMapper;
import com.example.public_transportation_query_system.service.IRouteService;

@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements IRouteService {

}
