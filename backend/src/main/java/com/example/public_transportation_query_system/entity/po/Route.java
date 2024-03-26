package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Route", description = "线路表")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "线路号")
    private String no;

    @Schema(description = "首班车时间")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime firstTime;

    @Schema(description = "末班车时间")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime lastTime;

    @Schema(description = "开通状态（0 计划开通、1 正常运营、2 暂停运营）")
    private Byte status;
}
