package com.example.public_transportation_query_system.entity.vo.request;

import java.util.ArrayList;
import java.util.List;

import com.example.public_transportation_query_system.entity.vo.BaseData;

import lombok.Data;

@Data
public class UserVO implements BaseData {

    private String username;

    private String password;

    private String email;

    private String roleIds;

    /**
     * 返回解析为 Integer 的 roleIds
     * @return
     */
    public List<Integer> getRoleIds() {
        String[] roleIdStrings = roleIds.split(",");
        List<Integer> roleIdIntegers = new ArrayList<>();

        for (int i = 0; i< roleIdStrings.length; i++) {
            roleIdIntegers.add(Integer.parseInt(roleIdStrings[i]));
        }
        // 若 roleIds 为空 则添加默认角色 id (1 用户)
        if (roleIdIntegers.size() == 0) {
            roleIdIntegers.add(1);
        };
        return roleIdIntegers;
    }

    @Override
    public String toString() {
        return "User{" +
            ", username = " + username +
            ", password = " + password +
            ", email = " + email +
            ", roleIds = [" + roleIds + "]" +
        "}";
    }
}
