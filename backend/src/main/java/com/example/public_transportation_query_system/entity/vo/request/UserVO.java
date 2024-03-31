package com.example.public_transportation_query_system.entity.vo.request;

import java.util.ArrayList;
import java.util.List;

import com.example.public_transportation_query_system.entity.po.Role;
import com.example.public_transportation_query_system.entity.po.UserRole;
import com.example.public_transportation_query_system.entity.vo.BaseData;

import lombok.Data;

@Data
public class UserVO implements BaseData {

    private Integer id = null;

    private String username = null;

    private String password = null;

    private String email = null;

    private Byte status = 1;

    private List<Role> roles = new ArrayList<>();

    /**
     * 根据 id (User.id) 和 roles 生成 UserRole (UserRole.id 为 null)
     * @return
     */
    public List<UserRole> getUserRoleList() {
        List<UserRole> userRoleList = new ArrayList<>();
        this.roles.stream()
            .distinct()
            .forEach(role -> userRoleList.add(
                new UserRole(null, id, role.getId())));
        return userRoleList;
    }
}
