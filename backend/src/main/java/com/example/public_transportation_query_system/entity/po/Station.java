package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Station", description = "")
public class Station implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "站点名")
    private String sitename;

    @Schema(description = "开通状态")
    private String status;

    @Schema(description = "经度")
    private Double longitude;

    @Schema(description = "纬度")
    private Double latitude;

    @Override
    public String toString() {
        return "Station{" +
            "id = " + id +
            ", sitename = " + sitename +
            ", status = " + status +
            ", longitude = " + longitude +
            ", latitude = " + latitude +
        "}";
    }
}
