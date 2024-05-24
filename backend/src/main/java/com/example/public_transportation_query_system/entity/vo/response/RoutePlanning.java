package com.example.public_transportation_query_system.entity.vo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoutePlanning {
    private String methods;
    private String length;
    private String target;
}
