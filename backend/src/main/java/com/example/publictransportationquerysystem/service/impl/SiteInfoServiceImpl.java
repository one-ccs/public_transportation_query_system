package com.example.publictransportationquerysystem.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.publictransportationquerysystem.entity.SiteInfo;
import com.example.publictransportationquerysystem.mapper.SiteInfoMapper;
import com.example.publictransportationquerysystem.service.ISiteInfoService;


@Service
public class SiteInfoServiceImpl extends ServiceImpl<SiteInfoMapper, SiteInfo> implements ISiteInfoService {

}
