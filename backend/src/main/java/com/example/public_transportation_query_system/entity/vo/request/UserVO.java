package com.example.public_transportation_query_system.entity.vo.request;

import java.util.ArrayList;
import java.util.List;

import com.example.public_transportation_query_system.entity.po.Role;
import com.example.public_transportation_query_system.entity.po.UserRole;
import com.example.public_transportation_query_system.entity.vo.BaseData;

import lombok.Data;

@Data
public class UserVO implements BaseData {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private Byte status;

    private List<Role> roles;

    /**
     * 根据 id (User.id) 和 roles 生成 UserRole (UserRole.id 为 null)
     * @return
     */
    public List<UserRole> getUserRoleList() {
        List<UserRole> userRoleList = new ArrayList<>();
        roles.forEach(role -> userRoleList.add(new UserRole(null, id, role.getId())));
        return userRoleList;
    }

    @Override
    public String toString() {
        return "UserVO{" +
            "id = " + id +
            ", username = " + username +
            ", password = " + password +
            ", email = " + email +
            ", roles = [" + roles + "]" +
        "}";
    }
}
