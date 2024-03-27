package com.example.public_transportation_query_system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.public_transportation_query_system.entity.po.Notice;
import com.example.public_transportation_query_system.entity.vo.response.NoticeVO;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    Page<NoticeVO> selectPage(Page<NoticeVO> page, @Param(Constants.WRAPPER) QueryWrapper<NoticeVO> query);

}
