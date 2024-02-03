package com.example.publictransportationquerysystem.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.publictransportationquerysystem.entity.Lost;
import com.example.publictransportationquerysystem.mapper.LostMapper;
import com.example.publictransportationquerysystem.service.ILostService;


@Service
public class LostServiceImpl extends ServiceImpl<LostMapper, Lost> implements ILostService {

}
