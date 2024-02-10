package com.example.public_transportation_query_system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.public_transportation_query_system.entity.po.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
