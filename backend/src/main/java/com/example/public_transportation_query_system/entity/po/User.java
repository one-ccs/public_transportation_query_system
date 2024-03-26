package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.public_transportation_query_system.entity.vo.BaseData;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "User", description = "用户表")
public class User implements Serializable, BaseData {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "状态（0 未激活、1 已激活、2 已注销）")
    private Byte status;

    @Schema(description = "注册日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerDatetime;

    public User(String username, String password, String email) {
        this(null, username, password, email, null, null);
    }

    @Override
    public String toString() {
        return "User{" +
            "id = " + id +
            ", username = " + username +
            ", password = " + password +
            ", email = " + email +
            ", status = " + status +
            ", registerDatetime = " + registerDatetime +
        "}";
    }
}
