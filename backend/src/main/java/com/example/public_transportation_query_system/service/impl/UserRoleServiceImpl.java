package com.example.public_transportation_query_system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.UserRole;
import com.example.public_transportation_query_system.mapper.UserRoleMapper;
import com.example.public_transportation_query_system.service.IUserRoleService;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    public int deleteAllByUid(Integer uid) {
        return userRoleMapper.deleteAllByUid(uid);
    }
}
