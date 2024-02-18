package com.example.public_transportation_query_system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.bo.UserBO;
import com.example.public_transportation_query_system.entity.dto.MyUserDetails;
import com.example.public_transportation_query_system.entity.po.Role;
import com.example.public_transportation_query_system.entity.po.User;
import com.example.public_transportation_query_system.entity.vo.BaseQuery;
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
        List<Role> roles = roleMapper.getRolesByUid(user.getId());

        return new MyUserDetails(username, user.getPassword(), roles, user);
    }

    /**
     * 通过用户名或邮箱地址查找用户
     * @param nameOrEmail 用户名/邮箱地址
     * @return
     */
    public User getUserByNameOrEmail(String nameOrEmail) {
        return this.query()
            .eq("username", nameOrEmail).or()
            .eq("email", nameOrEmail)
            .one();
    }

    public UserBO getUserWithRoles(User user) {
        return user.asViewObject(UserBO.class, v -> {
            v.setRoles(roleMapper.getRolesByUid(user.getId()));
        });
    }

    public boolean register(String username, String password, String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    public HashMap<String, Object> getPageUsers(BaseQuery query) {
        // 构造查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(query.getStartDatetime() != null, "register_datetime", query.getStartDatetime());
        queryWrapper.le(query.getEndDatetime() != null, "register_datetime", query.getEndDatetime());
        // 分页
        Page<User> page = new Page<>(
            Optional.ofNullable(query.getPage()).orElse(1),
            Optional.ofNullable(query.getPageSize()).orElse(10)
        );
        // 构造返回结构
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", this.count());
        map.put("list", this.list(page, queryWrapper).stream()
            .map(this::getUserWithRoles)
            .toList()
        );

        return map;
    }
}
