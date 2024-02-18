package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("route_station")
@Schema(name = "RouteStation", description = "线路-关联表站点")
public class RouteStation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "线路信息id")
    private Integer routeId;

    @Schema(description = "站点信息id")
    private Integer stationId;

    @Schema(description = "站点在线路中的顺序")
    private Byte sequence;

    @Override
    public String toString() {
        return "RouteStation{" +
            "id = " + id +
            ", routeId = " + routeId +
            ", stationId = " + stationId +
            ", sequence = " + sequence +
        "}";
    }
}
