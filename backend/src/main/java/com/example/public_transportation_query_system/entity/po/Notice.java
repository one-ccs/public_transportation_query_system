package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Notice", description = "公告表")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "发布日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
