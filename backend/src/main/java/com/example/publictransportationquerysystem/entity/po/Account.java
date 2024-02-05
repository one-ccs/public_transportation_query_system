package com.example.publictransportationquerysystem.entity.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.publictransportationquerysystem.entity.vo.BaseData;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "账户")
public class Account implements Serializable, BaseData {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "注册日期")
    private LocalDateTime registerDatetime;

    @Schema(description = "角色（user、admin）")
    private String role;

    @Schema(description = "状态（0 未激活、1 已激活、2 已注销）")
    private Byte status;

    @Override
    public String toString() {
        return "Account{" +
            "id = " + id +
            ", username = " + username +
            ", password = " + password +
            ", createDatetime = " + registerDatetime +
            ", role = " + role +
            ", status = " + status +
        "}";
    }
}
