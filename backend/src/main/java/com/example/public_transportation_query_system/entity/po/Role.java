package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Role", description = "角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "角色")
    private String name;

    @Schema(description = "中文名称")
    private String nameZh;

    @Override
    public String toString() {
        return "Role{" +
            "id = " + id +
            ", name = " + name +
            ", nameZh = " + nameZh +
        "}";
    }
}
