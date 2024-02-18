package com.example.public_transportation_query_system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.Role;
import com.example.public_transportation_query_system.entity.po.UserRole;
import com.example.public_transportation_query_system.mapper.RoleMapper;
import com.example.public_transportation_query_system.service.IRoleService;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    UserRoleServiceImpl userRoleServiceImpl;

    @Autowired
    RoleMapper roleMapper;

    /**
     * 返回用户的角色列表
     * @param uid 用户 id
     * @return
     */
    public List<Role> getRolesByUid(Integer uid) {
        return roleMapper.getRolesByUid(uid);
    }

    /**
     * 给用户添加多个角色
     * @param uid 用户 id
     * @param roleIds 角色 id 列表
     * @return 是否添加成功
     */
    public boolean addRoles(Integer uid, List<Integer> roleIds) {
        List<UserRole> userRoles = new ArrayList<>();
        roleIds.forEach(roleId -> {
            userRoles.add(new UserRole(null, uid, roleId));
        });
        return userRoleServiceImpl.saveOrUpdateBatch(userRoles);
    }
}
