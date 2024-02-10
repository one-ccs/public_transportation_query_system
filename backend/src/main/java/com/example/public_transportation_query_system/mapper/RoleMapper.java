package com.example.public_transportation_query_system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.public_transportation_query_system.entity.po.Role;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRolesByUid(Integer uid);
}
