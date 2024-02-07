package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;
import java.time.LocalTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Route", description = "")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "线路号")
    private String no;

    @Schema(description = "首班车时间")
    private LocalTime firstTime;

    @Schema(description = "末班车时间")
    private LocalTime lastTime;

    @Schema(description = "开通状态（0 未开通、1 已开通、2 暂停运营）")
    private Byte status;

    @Override
    public String toString() {
        return "Route{" +
            "id = " + id +
            ", no = " + no +
            ", firstTime = " + firstTime +
            ", lastTime = " + lastTime +
            ", status = " + status +
        "}";
    }
}
