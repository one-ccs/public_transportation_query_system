package com.example.publictransportationquerysystem.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.publictransportationquerysystem.entity.Ad;
import com.example.publictransportationquerysystem.mapper.AdMapper;
import com.example.publictransportationquerysystem.service.IAdService;

@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements IAdService {

}
