package com.example.public_transportation_query_system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.public_transportation_query_system.entity.po.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    Page<User> selectPage(Page<User> page, @Param(Constants.WRAPPER) QueryWrapper<User> query);
}
