package com.example.public_transportation_query_system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.Role;
import com.example.public_transportation_query_system.mapper.RoleMapper;
import com.example.public_transportation_query_system.service.IRoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    public List<Role> getRolesByUid(Integer uid) {
        return roleMapper.getRolesByUid(uid);
    }
}
