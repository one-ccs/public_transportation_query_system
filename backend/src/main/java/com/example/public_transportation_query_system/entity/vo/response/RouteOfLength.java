package com.example.public_transportation_query_system.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouteOfLength {
    private Integer id;
    private String no;
    private Double length;
}
