package com.example.public_transportation_query_system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.Role;
import com.example.public_transportation_query_system.entity.po.User;
import com.example.public_transportation_query_system.mapper.RoleMapper;
import com.example.public_transportation_query_system.mapper.UserMapper;
import com.example.public_transportation_query_system.service.IUserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getUserByNameOrEmail(username);
        if (user == null) throw new UsernameNotFoundException("不存在该用户");

        // 获取角色列表
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = roleMapper.getRolesByUid(user.getId());
        for(Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPassword())
            .authorities(authorities)
            .build();
    }

    public User getUserByNameOrEmail(String nameOrEmail) {
        return this.query()
            .eq("username", nameOrEmail).or()
            .eq("email", nameOrEmail)
            .one();
    }
}
