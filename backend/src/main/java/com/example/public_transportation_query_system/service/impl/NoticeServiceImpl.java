package com.example.public_transportation_query_system.service.impl;

import com.example.public_transportation_query_system.entity.po.Notice;
import com.example.public_transportation_query_system.mapper.NoticeMapper;
import com.example.public_transportation_query_system.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
