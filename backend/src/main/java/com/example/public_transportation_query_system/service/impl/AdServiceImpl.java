package com.example.public_transportation_query_system.service.impl;

import com.example.public_transportation_query_system.entity.po.Ad;
import com.example.public_transportation_query_system.mapper.AdMapper;
import com.example.public_transportation_query_system.service.IAdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements IAdService {

}
