package com.example.publictransportationquerysystem.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User对象")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "创建日期")
    private LocalDateTime createDatetime;

    @Schema(description = "角色（user、admin）")
    private String role;

    @Schema(description = "状态（0 未激活、1 已激活、2 已注销）")
    private Byte status;

    @Override
    public String toString() {
        return "User{" +
            "id = " + id +
            ", username = " + username +
            ", password = " + password +
            ", createDatetime = " + createDatetime +
            ", role = " + role +
            ", status = " + status +
        "}";
    }
}
