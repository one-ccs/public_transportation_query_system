package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("station_route")
@Schema(name = "StationRoute", description = "站点-线路关联表")
public class StationRoute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "站点线路关联 id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "站点 id")
    private Integer stationId;

    @Schema(description = "线路 id")
    private Integer routeId;

}
