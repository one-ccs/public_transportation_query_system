package com.example.public_transportation_query_system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.public_transportation_query_system.entity.po.UserRole;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    void removeBatchByUid(Integer uid);

}
