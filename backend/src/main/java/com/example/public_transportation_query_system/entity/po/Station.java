package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Station", description = "站点表")
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "站点 id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "站点名")
    private String sitename;

    @Schema(description = "经度")
    private Double longitude;

    @Schema(description = "纬度")
    private Double latitude;

    @Schema(description = "开通状态（0 计划开通、1 正常运营、2 暂停运营）")
    private Byte status;

    @Schema(description = "开通日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openingDatetime;
}
