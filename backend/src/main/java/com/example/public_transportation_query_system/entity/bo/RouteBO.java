package com.example.public_transportation_query_system.entity.bo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RouteBO {

    private Integer id;

    private String no;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime firstTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime lastTime;

    private Byte status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openingDatetime;

    private List<StationBO> stations;

}
