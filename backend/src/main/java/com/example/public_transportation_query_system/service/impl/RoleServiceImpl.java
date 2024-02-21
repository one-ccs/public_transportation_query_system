package com.example.public_transportation_query_system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.public_transportation_query_system.entity.po.Role;
import com.example.public_transportation_query_system.entity.vo.request.UserVO;
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
     * @param userVO userVO 包装类
     * @return 是否添加成功
     */
    public boolean addRoles(UserVO userVO) {
        return userRoleServiceImpl.saveOrUpdateBatch(userVO.getUserRoleList());
    }
}
