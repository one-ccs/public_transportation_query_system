package com.example.public_transportation_query_system.service.impl;

import com.example.public_transportation_query_system.entity.po.Lost;
import com.example.public_transportation_query_system.mapper.LostMapper;
import com.example.public_transportation_query_system.service.ILostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LostServiceImpl extends ServiceImpl<LostMapper, Lost> implements ILostService {

}
