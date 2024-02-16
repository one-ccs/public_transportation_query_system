package com.example.public_transportation_query_system.entity.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Lost", description = "失物招领表")
public class Lost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "描述")
    private String describe;

    @Schema(description = "图片链接")
    private String imgUrl;

    @Schema(description = "拾取地点")
    private String address;

    @Schema(description = "拾取时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pickDatetime;

    @Schema(description = "认领时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime claimDatetime;

    @Schema(description = "认领状态（0 待认领、1 已认领）")
    private Byte status;

    @Override
    public String toString() {
        return "Lost{" +
            "id = " + id +
            ", describe = " + describe +
            ", imgUrl = " + imgUrl +
            ", address = " + address +
            ", pickDatetime = " + pickDatetime +
            ", claimDatetime = " + claimDatetime +
            ", status = " + status +
        "}";
    }
}
