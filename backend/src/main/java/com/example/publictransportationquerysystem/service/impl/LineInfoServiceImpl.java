package com.example.publictransportationquerysystem.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.publictransportationquerysystem.entity.LineInfo;
import com.example.publictransportationquerysystem.mapper.LineInfoMapper;
import com.example.publictransportationquerysystem.service.ILineInfoService;


@Service
public class LineInfoServiceImpl extends ServiceImpl<LineInfoMapper, LineInfo> implements ILineInfoService {

}
