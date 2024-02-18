package com.example.public_transportation_query_system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.bo.UserBO;
import com.example.public_transportation_query_system.entity.dto.MyUserDetails;
import com.example.public_transportation_query_system.entity.po.Role;
import com.example.public_transportation_query_system.entity.po.User;
import com.example.public_transportation_query_system.entity.vo.request.QueryUserVO;
import com.example.public_transportation_query_system.entity.vo.request.UserVO;
import com.example.public_transportation_query_system.mapper.UserMapper;
import com.example.public_transportation_query_system.service.IUserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    RoleServiceImpl roleServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getUserByNameOrEmail(username, username);
        if (user == null) throw new UsernameNotFoundException("不存在该用户");
        List<Role> roles = roleServiceImpl.getRolesByUid(user.getId());

        return new MyUserDetails(username, user.getPassword(), roles, user);
    }

    /**
     * 通过用户名或邮箱地址查找用户
     * @param name 用户名
     * @param email 邮箱地址
     * @return
     */
    public User getUserByNameOrEmail(String username, String email) {
        return this.query()
            .eq("username", username).or()
            .eq("email", email)
            .one();
    }

    public UserBO getUserWithRoles(User user) {
        return user.asViewObject(UserBO.class, v -> {
            v.setRoles(roleServiceImpl.getRolesByUid(user.getId()));
        });
    }

    public boolean register(String username, String password, String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    public HashMap<String, Object> getPageUsers(QueryUserVO query) {
        // 构造查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(query.getStartDatetime() != null, "register_datetime", query.getStartDatetime());
        queryWrapper.le(query.getEndDatetime() != null, "register_datetime", query.getEndDatetime());
        queryWrapper.like(StringUtils.isNotBlank(query.getUsername()), "username", query.getUsername());
        // 分页
        Page<User> page = new Page<>(
            Optional.ofNullable(query.getPageIndex()).orElse(1),
            Optional.ofNullable(query.getPageSize()).orElse(10)
        );
        // 构造返回结构
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", this.count());
        map.put("list", this.list(page, queryWrapper).stream()
            .map(this::getUserWithRoles)
            // filterFlag 为 0 时不进行过滤
            // filterFlag 为 1 时筛选角色 id = 1 的用户
            // filterFlag 为 2 时筛选角色 id > 1 的用户
            .filter(userBO -> {
                if (query.getFilterFlag() == 0) return true;
                if (query.getFilterFlag() == 1) {
                    return userBO.getRoles().stream()
                        .anyMatch(role -> role.getId() == 1);
                }
                if (query.getFilterFlag() == 2) {
                    return userBO.getRoles().stream()
                            .anyMatch(role -> role.getId() > 1);
                }
                return false;
            })
            .toList()
        );

        return map;
    }

    /**
     * 向数据库中添加用户并设置角色
     * @param userVO
     * @return
     */
    public Object addUser(UserVO userVO) {
        // 构造 User 并加密密码
        User newUser = userVO.asViewObject(User.class, v -> v.setPassword(passwordEncoder.encode(v.getPassword())));
        // 添加用户
        if (this.save(newUser)) {
            // 获取新用户
            User user = this.getUserByNameOrEmail(userVO.getUsername(), userVO.getEmail());
            // 设置角色
            return roleServiceImpl.addRoles(user.getId(), userVO.getRoleIds());
        }
        return false;
    }
}
