package com.example.public_transportation_query_system.entity.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BasePageQuery {

    private Integer pageIndex = 1;

    private Integer pageSize = 10;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDatetime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDatetime;

    @Override
    public String toString() {
        return "BasePageQuery{" +
            "pageIndex = " + pageIndex +
            ", pageSize = " + pageSize +
            ", startDatetime = " + startDatetime +
            ", endDatetime = " + endDatetime +
        "}";
    }
}
