package com.example.publictransportationquerysystem.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(description = "Notice对象")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "发布日期")
    private LocalDateTime releaseDatetime;

    @Schema(description = "状态（0 不显示、1 显示）")
    private Byte status;

    @Override
    public String toString() {
        return "Notice{" +
            "id = " + id +
            ", content = " + content +
            ", releaseDatetime = " + releaseDatetime +
            ", status = " + status +
        "}";
    }
}