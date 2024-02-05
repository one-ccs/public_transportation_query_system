package com.example.publictransportationquerysystem.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@TableName("line_info")
@Data
@Schema(description = "LineInfo对象")
public class LineInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "线路号")
    private String no;

    @Schema(description = "首班车时间")
    private LocalTime firstTime;

    @Schema(description = "末班车时间")
    private LocalTime lastTime;

    @Schema(description = "开通状态（0 未开通、1 已开通、2 暂停运营）")
    private Byte status;

    @Override
    public String toString() {
        return "LineInfo{" +
            "id = " + id +
            ", no = " + no +
            ", firstTime = " + firstTime +
            ", lastTime = " + lastTime +
            ", status = " + status +
        "}";
    }
}
