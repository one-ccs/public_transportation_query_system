package com.example.public_transportation_query_system.entity.bo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.public_transportation_query_system.entity.po.Role;

import lombok.Data;

@Data
public class UserBO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerDatetime;

    private Byte status;

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles.stream()
            .map(role -> {
                role.setName(role.getName().replace("ROLE_", ""));
                return role;
            })
            .toList();
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserBO{" +
            "id = " + id +
            ", username = " + username +
            ", password = " + password +
            ", email = " + email +
            ", status = " + status +
            ", registerDatetime = " + registerDatetime +
            ", roles = " + roles +
        "}";
    }
}
