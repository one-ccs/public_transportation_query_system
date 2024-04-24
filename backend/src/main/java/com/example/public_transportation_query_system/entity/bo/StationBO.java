package com.example.public_transportation_query_system.entity.bo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class StationBO {

    private Integer id;

    private String sitename;

    private Double longitude;

    private Double latitude;

    private Byte status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openingDatetime;

    private Integer routeId;

    private Byte sequence;

    private Double distance;

}
