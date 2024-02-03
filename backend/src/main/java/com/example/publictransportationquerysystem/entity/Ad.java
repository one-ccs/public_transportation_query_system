package com.example.publictransportationquerysystem.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ad对象")
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "广告类型（）")
    private Byte type;

    @Schema(description = "描述")
    private String describe;

    @Schema(description = "广告图片链接")
    private String imgUrl;

    @Schema(description = "跳转链接")
    private String jumpUrl;

    @Schema(description = "发布日期")
    private LocalDateTime releaseDatetime;

    @Override
    public String toString() {
        return "Ad{" +
            "id = " + id +
            ", type = " + type +
            ", describe = " + describe +
            ", imgUrl = " + imgUrl +
            ", jumpUrl = " + jumpUrl +
            ", releaseDatetime = " + releaseDatetime +
        "}";
    }
}
