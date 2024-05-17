package com.example.public_transportation_query_system.entity.vo.request;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.public_transportation_query_system.entity.bo.StationBO;
import com.example.public_transportation_query_system.entity.po.RouteStation;
import com.example.public_transportation_query_system.entity.vo.BaseData;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RouteVO implements BaseData {

    private Integer id;

    private String no;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime firstTime;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime lastTime;

    private Byte status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime openingDatetime;

    private List<StationBO> stations = new ArrayList<>();

    /**
     * 返回线路途径站点对应的 线路站点关联表
     * @return
     */
    public List<RouteStation> getRouteStations() {
        List<RouteStation> routeStations = new ArrayList<>();
        this.stations.stream()
            .distinct()
            .forEach(station -> routeStations
                .add(new RouteStation(null, this.id, station.getId(), station.getSequence())));
        return routeStations;
    }

}
