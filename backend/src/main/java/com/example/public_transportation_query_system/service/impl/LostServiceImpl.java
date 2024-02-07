package com.example.public_transportation_query_system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.Lost;
import com.example.public_transportation_query_system.mapper.LostMapper;
import com.example.public_transportation_query_system.service.ILostService;

@Service
public class LostServiceImpl extends ServiceImpl<LostMapper, Lost> implements ILostService {

}
