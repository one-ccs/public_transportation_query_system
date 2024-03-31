package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@TableName("user_role")
@Schema(name = "UserRole", description = "用户-角色关联表")
@AllArgsConstructor
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户角色关联 id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户 id")
    private Integer uid;

    @Schema(description = "角色 id")
    private Integer rid;
}
