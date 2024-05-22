package com.example.public_transportation_query_system.entity.vo.response;

import lombok.Data;

@Data
public class LostStatusCount {
    private Byte status;
    private Integer count;
}
