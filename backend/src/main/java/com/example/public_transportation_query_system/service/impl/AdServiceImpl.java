package com.example.public_transportation_query_system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.Ad;
import com.example.public_transportation_query_system.mapper.AdMapper;
import com.example.public_transportation_query_system.service.IAdService;

@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements IAdService {

}
