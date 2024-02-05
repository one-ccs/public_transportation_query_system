package com.example.publictransportationquerysystem.entity.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@TableName("site_info")
@Data
@Schema(description = "SiteInfo对象")
public class SiteInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "站点名")
    private String sitename;

    @Schema(description = "开通状态")
    private String status;

    @Override
    public String toString() {
        return "SiteInfo{" +
            "id = " + id +
            ", sitename = " + sitename +
            ", status = " + status +
        "}";
    }
}
