package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Ad", description = "广告表")
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "广告类型")
    private Byte type;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "描述")
    @TableField("`describe`")
    private String describe;

    @Schema(description = "广告图片链接")
    private String imgUrl;

    @Schema(description = "跳转链接")
    private String jumpUrl;

    @Schema(description = "开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDatetime;

    @Schema(description = "结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDatetime;

    @Override
    public String toString() {
        return "Ad{" +
            "id = " + id +
            ", type = " + type +
            ", title = " + title +
            ", describe = " + describe +
            ", imgUrl = " + imgUrl +
            ", jumpUrl = " + jumpUrl +
            ", startDatetime = " + startDatetime +
            ", endDatetime = " + endDatetime +
        "}";
    }
}
